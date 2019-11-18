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
            // Initiator flow logic goes here.
            val notary = getNotary()

            val me = getMe()

            val createCommand = getCommand(me)

            val todoState = getTodo(me)

            progressTracker.currentStep = BUILD_TRANSACTION
            val tx = getTx(notary, todoState, createCommand)

            progressTracker.currentStep = SIGN_TRANSACTION
            val ptx = signTransaction(tx, me)

            return subFlow(FinalityFlow(transaction = ptx, sessions = listOf(), progressTracker = FINALISING.childProgressTracker()))
        }

        private fun signTransaction(tx: TransactionBuilder, me: Party): SignedTransaction {
            val myKeysToSign = listOf(me.owningKey)
            return serviceHub.signInitialTransaction(tx, myKeysToSign)
        }

        private fun getTx(notary: Party, todoState: TodoState, createCommand: Command<TodoContract.Commands.Create>): TransactionBuilder {
            val txBuilder = TransactionBuilder(notary)
            txBuilder.withItems(StateAndContract(todoState, TodoContract.TODO_CONTRACT_ID), createCommand)

            progressTracker.currentStep = VERIFY_TRANSACTION
            txBuilder.verify(serviceHub)
            return txBuilder
        }

        private fun getTodo(me: Party) = TodoState(owner = me, task = task)

        private fun getCommand(me: Party) =
                Command(TodoContract.Commands.Create(), listOf(me.owningKey))

        private fun getMe() = this.ourIdentity

        private fun getNotary() = serviceHub.networkMapCache.notaryIdentities.single()
    }
}
