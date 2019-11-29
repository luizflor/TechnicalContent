package com.todo.flows

import co.paralleluniverse.fibers.Suspendable
import com.todo.contracts.TodoContract
import com.todo.states.TodoState
import com.todo.states.TodoStatus
import net.corda.core.contracts.Command
import net.corda.core.contracts.StateAndRef
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.flows.*
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party
import net.corda.core.node.StatesToRecord
import net.corda.core.node.services.Vault
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.serialization.CordaSerializable
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker
import java.lang.IllegalArgumentException

/**
 * This flow complete todo task by consuming its state if there is one member involved
 * If more members are involved it mark the task complete and only consumes it after all members acknowledge completion
 */
object CompleteFlow {
    @CordaSerializable
    data class Info(val taskId: UniqueIdentifier)

    @InitiatingFlow
    @StartableByRPC
    class CompleteSender(private val taskId: UniqueIdentifier) : FlowLogic<SignedTransaction>() {

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
            val transaction: TransactionBuilder = transaction()
            val signedTransaction: SignedTransaction = verifyAndSign(transaction)
            val sessions = ArrayList<FlowSession>()
            if (transaction.outputStates().isEmpty()) {
                return finality(signedTransaction, sessions)
            }
            val participants = (transaction.outputStates().single().data as TodoState).participants - this.ourIdentity - getNotary()
            participants.forEach { sessions.add(initiateFlow(it as Party)) }
            val stx = collectSignatures(signedTransaction, sessions)
            return finality(stx, sessions)
        }

        private fun transaction() : TransactionBuilder {
            progressTracker.currentStep = BUILD_TRANSACTION
            val notary = serviceHub.networkMapCache.notaryIdentities.single()
            val txBuilder = TransactionBuilder(notary)
            val command = Command(TodoContract.Commands.Complete(), listOf(this.ourIdentity.owningKey))
            txBuilder.addCommand(command)
            val state = getTodoStateAndRef(this.taskId)

            txBuilder.addInputState(state)

            val todo = state.state.data
            if(todo.participants.size > todo.participantsCompleted.size) {
                val todoStateOutput = todo.copy(status = TodoStatus.Completed, participantsCompleted = (todo.participantsCompleted + this.ourIdentity) as MutableList<AbstractParty>)
                txBuilder.addOutputState(todoStateOutput)
            }

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

        @Suspendable
        private fun collectSignatures(ptx: SignedTransaction, counterpartySession: List<FlowSession>): SignedTransaction {
            return subFlow(CollectSignaturesFlow(
                    partiallySignedTx = ptx,
                    sessionsToCollectFrom = counterpartySession,
                    progressTracker = AddParticipantsFlow.AddParticipantsSender.Companion.COLLECTING_SIGNATURES.childProgressTracker()
            ))
        }
        private fun getTodoStateAndRef(linearId: UniqueIdentifier): StateAndRef<TodoState> {
            val criteria = QueryCriteria.LinearStateQueryCriteria(linearId = listOf(linearId),status =Vault.StateStatus.UNCONSUMED)
            return serviceHub.vaultService.queryBy(TodoState::class.java,criteria).states.firstOrNull() ?: throw IllegalArgumentException("TodoState with linearId $linearId not found. ")
        }

        private fun getNotary() = serviceHub.networkMapCache.notaryIdentities.single()
    }

    @InitiatedBy(CompleteSender::class)
    class CompleteResponder(val counterpartySession: FlowSession) : FlowLogic<Unit>() {
        @Suspendable
        override fun call() {
            // Responder flow logic goes here.
            // signing transaction
            val signedTransactionFlow = object : SignTransactionFlow(counterpartySession) {
                override fun checkTransaction(stx: SignedTransaction) {
                }
            }
            val txId = subFlow(signedTransactionFlow).id

            subFlow(ReceiveFinalityFlow(
                    otherSideSession = counterpartySession,
                    expectedTxId = txId,
                    statesToRecord = StatesToRecord.ONLY_RELEVANT
            ))
        }
    }
}
