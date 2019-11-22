package com.todo.flows

import co.paralleluniverse.fibers.Suspendable
import com.todo.contracts.TodoContract
import com.todo.states.TodoState
import com.todo.states.TodoStatus
import net.corda.core.contracts.Command
import net.corda.core.contracts.StateAndRef
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.flows.*
import net.corda.core.node.services.Vault
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.serialization.CordaSerializable
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker
import java.lang.IllegalArgumentException

/**
 * This flow evolves the todo task from New to Cancel or Cancel back to New. Only one party is involved.
 * Notary will create a transaction record containing reference to the previous transaction.
 */
object CancelFlow {
    @CordaSerializable
    data class Info(val taskId: UniqueIdentifier, val isCancel: Boolean = true)

    @InitiatingFlow
    @StartableByRPC
    class CancelSender(private val taskId: UniqueIdentifier, val isCancel: Boolean= true) : FlowLogic<SignedTransaction>() {

        constructor(info: Info): this(taskId=info.taskId,
                isCancel = info.isCancel)

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

        private fun transaction() : TransactionBuilder {
            progressTracker.currentStep = BUILD_TRANSACTION
            val notary = serviceHub.networkMapCache.notaryIdentities.single()
            val txBuilder = TransactionBuilder(notary)
            val command = Command(TodoContract.Commands.Cancel(), listOf(this.ourIdentity.owningKey))
            txBuilder.addCommand(command)
            val state = getTodoStateAndRef(this.taskId)
            txBuilder.addInputState(state)
            txBuilder.addOutputState(state.state.data.copy(status = if (isCancel) TodoStatus.Canceled else TodoStatus.New))

            return txBuilder
        }

        private fun verifyAndSign(transaction: TransactionBuilder): SignedTransaction {
            progressTracker.currentStep = SIGN_TRANSACTION
            transaction.verify(serviceHub)
            return serviceHub.signInitialTransaction(transaction)
        }

        @Suspendable
        private fun finality(stx: SignedTransaction, counterpartySession: List<FlowSession>) =
                subFlow(FinalityFlow(transaction = stx, sessions = counterpartySession, progressTracker = FINALISING.childProgressTracker()))

        private fun getTodoStateAndRef(linearId: UniqueIdentifier): StateAndRef<TodoState> {
            val criteria = QueryCriteria.LinearStateQueryCriteria(linearId = listOf(linearId)).and(QueryCriteria.VaultQueryCriteria(status = Vault.StateStatus.UNCONSUMED))
            return serviceHub.vaultService.queryBy(TodoState::class.java,criteria).states.firstOrNull() ?: throw IllegalArgumentException("TodoState with linearId $linearId not found. ")
        }
    }
}
