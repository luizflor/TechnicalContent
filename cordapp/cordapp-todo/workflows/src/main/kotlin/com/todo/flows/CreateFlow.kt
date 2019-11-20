package com.todo.flows

import co.paralleluniverse.fibers.Suspendable
import com.todo.contracts.TodoContract
import com.todo.states.TodoState
import net.corda.core.contracts.Command
import net.corda.core.contracts.StateAndContract
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.serialization.CordaSerializable
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

/**
 * This flow creates a todo task with one node party. Only one party is involved.
 * Notary is not involved
 */
object CreateFlow {
    @CordaSerializable
    data class Info(val task: String)

    @InitiatingFlow
    @StartableByRPC
    class CreateSender(val task: String) : FlowLogic<SignedTransaction>() {

        constructor(info: Info): this(task=info.task)

        companion object {
            object BUILD_TRANSACTION : ProgressTracker.Step("Build the transaction")
            object VERIFY_TRANSACTION : ProgressTracker.Step("Verify the transaction")
            object SIGN_TRANSACTION : ProgressTracker.Step("Sign the transaction")
            object FINALISING : ProgressTracker.Step("Finalising transaction") {
                override fun childProgressTracker() = FinalityFlow.tracker()
            }

            fun tracker() = ProgressTracker(
                    BUILD_TRANSACTION,
                    VERIFY_TRANSACTION,
                    SIGN_TRANSACTION,
                    FINALISING
            )
        }

        override val progressTracker = tracker()

        @Suspendable
        override fun call(): SignedTransaction {
            val transaction: TransactionBuilder = transaction()
            val signedTransaction: SignedTransaction = verifyAndSign(transaction)
            val sessions = listOf<FlowSession>()
            return finality(signedTransaction, sessions)
        }


        @Suspendable
        private fun finality(stx: SignedTransaction, counterpartySession: List<FlowSession>) =
                subFlow(FinalityFlow(transaction = stx, sessions = counterpartySession, progressTracker = FINALISING.childProgressTracker()))

        private fun transaction() : TransactionBuilder {
            progressTracker.currentStep = BUILD_TRANSACTION
            val notary = serviceHub.networkMapCache.notaryIdentities.single()
            val txBuilder = TransactionBuilder(notary)
            val command = Command(TodoContract.Commands.Create(), listOf(this.ourIdentity.owningKey))
            val state = TodoState(owner = this.ourIdentity, task =this.task)
            txBuilder.withItems(StateAndContract(state,TodoContract.TODO_CONTRACT_ID), command)
            return txBuilder
        }

        private fun verifyAndSign(transaction: TransactionBuilder): SignedTransaction {
            progressTracker.currentStep = SIGN_TRANSACTION
            transaction.verify(serviceHub)
            return serviceHub.signInitialTransaction(transaction)
        }
    }
}
