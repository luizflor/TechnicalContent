package com.todo.contracts

import com.todo.states.TodoState
import com.todo.states.TodoStatus
import net.corda.core.contracts.*
import net.corda.core.transactions.LedgerTransaction
import java.security.PublicKey

// ************
// * Contract *
// ************
class TodoContract : Contract {
    companion object {
        // Used to identify our contract when building a transaction.
        @JvmStatic
        val TODO_CONTRACT_ID = TodoContract::class.qualifiedName!!//"com.todo.contracts.TodoContract"
    }

    // A transaction is valid if the verify() function of the contract of all the transaction's input and output states
    // does not throw an exception.
    override fun verify(tx: LedgerTransaction) {
        // Verification logic goes here.
        val command = tx.commands.requireSingleCommand<Commands>()
        val signers = command.signers.toSet()
        when (command.value) {
            is Commands.Create -> verifyCreate(tx, signers)
            is Commands.Cancel -> verifyCancel(tx, signers)
            is Commands.Complete -> verifyComplete(tx, signers)
        }
    }

    private fun verifyCreate(tx: LedgerTransaction, signers: Set<PublicKey>) {
        requireThat {
            "Todo transaction should have no input." using (tx.inputs.isEmpty())
            "Todo transaction should have output." using (tx.outputs.isNotEmpty())
            val todo = tx.outputsOfType<TodoState>().single()
            "Initial status must be new" using(todo.status == TodoStatus.New)
            verifySignature(signers, todo)
        }
    }

    private fun verifySignature(signers: Set<PublicKey>, todo: TodoState) {
        requireThat {
            "Signing is required" using (signers == todo.participants.map { it.owningKey }.toSet())
        }
    }

    private fun verifyCancel(tx: LedgerTransaction, signers: Set<PublicKey>) {
        requireThat {
            "Todo transaction should have input." using (tx.inputs.isNotEmpty())
            "Todo transaction should have  output." using (tx.outputs.isNotEmpty())
            val todoInput = tx.inputsOfType<TodoState>().single()
            "Initial status must be new or cancel" using(todoInput.status == TodoStatus.New || todoInput.status == TodoStatus.Canceled)
            val todoOutput = tx.outputsOfType<TodoState>().single()
            "Output status must be canceled when input status is new or output status is new when input status is canceled " using(
                    (todoInput.status == TodoStatus.New  && todoOutput.status == TodoStatus.Canceled)
                            || (todoInput.status == TodoStatus.Canceled  && todoOutput.status == TodoStatus.New))
            verifySignature(signers, todoInput)
            verifySignature(signers, todoOutput)
        }
    }

    private fun verifyComplete(tx: LedgerTransaction, signers: Set<PublicKey>) {
        requireThat {
            "Todo transaction should have input." using (tx.inputs.isNotEmpty())
            "Todo transaction should have no output." using (tx.outputs.isEmpty())
            val todo = tx.inputsOfType<TodoState>().single()
            "Initial status must be new" using(todo.status == TodoStatus.New)
            verifySignature(signers, todo)
        }
    }

    private fun verifyCommon(tx: LedgerTransaction) {
        requireThat {
            "Todo transaction should have input." using (tx.inputs.isNotEmpty())
            "Todo transaction should have no output." using (tx.outputs.isEmpty())
        }
    }

    // Used to indicate the transaction's intent.
    interface Commands : CommandData {
        class Create: TypeOnlyCommandData(), Commands
        class Cancel: TypeOnlyCommandData(), Commands
        class Complete: TypeOnlyCommandData(), Commands
    }
}