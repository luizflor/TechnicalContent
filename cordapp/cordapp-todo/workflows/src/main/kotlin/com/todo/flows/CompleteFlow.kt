package com.todo.flows

import co.paralleluniverse.fibers.Suspendable
import com.todo.contracts.TodoContract
import com.todo.states.TodoState
import net.corda.core.contracts.Command
import net.corda.core.contracts.StateAndRef
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.node.services.Vault
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.serialization.CordaSerializable
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker
import java.lang.IllegalArgumentException

/**
 * This flow complete todo task by consuming its state. Only one party is involved.
 */
object CompleteFlow {
    @CordaSerializable
    data class Info(val taskId: UniqueIdentifier)

    @InitiatingFlow
    @StartableByRPC
    class CompleteSender(val taskId: UniqueIdentifier) : FlowLogic<SignedTransaction>() {

        constructor(info: Info): this(taskId=info.taskId)

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

            val completeCommand = getCommand(me)

            val todoState = getTodo(taskId)

            progressTracker.currentStep = BUILD_TRANSACTION
            val tx = getTx(notary, todoState, completeCommand)

            progressTracker.currentStep = SIGN_TRANSACTION
            val ptx = signTransaction(tx, me)

            return subFlow(FinalityFlow(transaction = ptx, sessions = listOf(), progressTracker = FINALISING.childProgressTracker()))
        }

        private fun signTransaction(tx: TransactionBuilder, me: Party): SignedTransaction {
            val myKeysToSign = listOf(me.owningKey)
            return serviceHub.signInitialTransaction(tx, myKeysToSign)
        }

        private fun getTx(notary: Party, todoState:  StateAndRef<TodoState> , command: Command<TodoContract.Commands.Complete>): TransactionBuilder {
            val txBuilder = TransactionBuilder(notary)
            txBuilder.addCommand(command)
            txBuilder.addInputState(todoState)

            progressTracker.currentStep = VERIFY_TRANSACTION
            txBuilder.verify(serviceHub)
            return txBuilder
        }

        private fun getTodo(linearId: UniqueIdentifier): StateAndRef<TodoState> {
            val criteria = QueryCriteria.LinearStateQueryCriteria(linearId = listOf(linearId)).and(QueryCriteria.VaultQueryCriteria(status = Vault.StateStatus.UNCONSUMED))
            val todoStateAndRef = serviceHub.vaultService.queryBy(TodoState::class.java,criteria).states.firstOrNull() ?: throw IllegalArgumentException("TodoState with linearId $linearId not found. ")
            return todoStateAndRef
        }

        private fun getCommand(me: Party) =
                Command(TodoContract.Commands.Complete(), listOf(me.owningKey))

        private fun getMe() = this.ourIdentity

        private fun getNotary() = serviceHub.networkMapCache.notaryIdentities.single()
    }
}
