package com.count.contracts

import com.count.states.CountState
import net.corda.core.contracts.*
import net.corda.core.transactions.LedgerTransaction
import java.security.PublicKey

// ************
// * Contract *
// ************
class CountContract : Contract {
    companion object {
        // Used to identify our contract when building a transaction.
        @JvmStatic
        val ID = CountContract::class.qualifiedName!!
    }

    // A transaction is valid if the verify() function of the contract of all the transaction's input and output states
    // does not throw an exception.
    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<Commands>()
        val signers = command.signers.toSet()
        when (command.value) {
            is Commands.Start -> verifyStart(tx, signers)
            is Commands.Add -> verifyAdd(tx, signers)
        }
    }

    private fun keysFromParticipants(countState: CountState): Set<PublicKey> {
        return countState.participants.map {
            it.owningKey
        }.toSet()
    }

    private fun verifyAdd(tx: LedgerTransaction, signers: Set<PublicKey>) {
        requireThat {
            "Todo transaction should have no input." using (tx.inputs.isNotEmpty())
            "Todo transaction should have output." using (tx.outputs.isNotEmpty())
            val countInputState = tx.inputsOfType<CountState>().single()
            val countOutputState = tx.outputsOfType<CountState>().single()
            countOutputState.Count > countInputState.Count

            "Input partners:  ${countInputState.partnerA.name} cannot be the same as ${countInputState.partnerB.name}" using (countInputState.partnerA.name != countInputState.partnerB.name)
            "Output partners:  ${countOutputState.partnerA.name} cannot be the same as ${countOutputState.partnerB.name}" using (countOutputState.partnerA.name != countOutputState.partnerB.name)

            "Output partnerA ${countOutputState.partnerA.name} must be one either ${countInputState.partnerA.name} or ${countInputState.partnerB.name}" using
                    ( countInputState.partnerA.name == countOutputState.partnerA.name || countInputState.partnerB.name == countOutputState.partnerA.name )

            "Signing is required" using (signers ==  keysFromParticipants(countInputState) union keysFromParticipants(countOutputState))
        }
    }

    private fun verifyStart(tx: LedgerTransaction, signers: Set<PublicKey>) {
        requireThat {
            "Todo transaction should have no input." using (tx.inputs.isEmpty())
            "Todo transaction should have output." using (tx.outputs.isNotEmpty())
            val countOutputState = tx.outputsOfType<CountState>().single()
            countOutputState.Count == 0
            "Output partners:  ${countOutputState.partnerA.name} cannot be the same as ${countOutputState.partnerB.name}" using (countOutputState.partnerA.name != countOutputState.partnerB.name)
            "Signing is required" using (signers == keysFromParticipants(countOutputState))
        }
    }

    // Used to indicate the transaction's intent.
    interface Commands : CommandData {
        class Start : TypeOnlyCommandData(), Commands
        class Add : TypeOnlyCommandData(), Commands
    }
}