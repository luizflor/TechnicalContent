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
 * This flow evolves the todo task from with new participants
 * Notary will create a transaction record containing reference to the previous transaction.
 */
object AddParticipantsFlow {
    @CordaSerializable
    data class Info(val taskId: UniqueIdentifier, val participant: Party)

    @InitiatingFlow
    @StartableByRPC
    class AddParticipantsSender(val taskId: UniqueIdentifier, val participant: Party) : FlowLogic<SignedTransaction>() {

        constructor(info: Info) : this(taskId = info.taskId, participant = info.participant)

        companion object {
            object BUILD_TRANSACTION : ProgressTracker.Step("Build the transaction")
            object VERIFY_TRANSACTION : ProgressTracker.Step("Verify the transaction")
            object SIGN_TRANSACTION : ProgressTracker.Step("Sign the transaction")
            object COLLECTING_SIGNATURES : ProgressTracker.Step("Collecting counterparty signature") {
                override fun childProgressTracker() = CollectSignaturesFlow.tracker()
            }
            object FINALISING : ProgressTracker.Step("Finalising transaction") {
                override fun childProgressTracker() = FinalityFlow.tracker()
            }

            fun tracker() = ProgressTracker(
                    BUILD_TRANSACTION,
                    VERIFY_TRANSACTION,
                    SIGN_TRANSACTION,
                    FINALISING,
                    COLLECTING_SIGNATURES
            )
        }

        override val progressTracker = tracker()

        @Suspendable
        override fun call(): SignedTransaction {
            // Initiator flow logic goes here.
            val notary = getNotary()

            val me = getMe()

            val todoState = getTodo(taskId)

            val participants = todoState.state.data.participants.filter { it.owningKey == participant.owningKey }.toMutableList()
            if (participants.isEmpty()) {
                participants.addAll(todoState.state.data.participants)
                participants.add(participant)
            }

            progressTracker.currentStep = BUILD_TRANSACTION
            val tx = getTx(notary, todoState, participants)

            progressTracker.currentStep = SIGN_TRANSACTION
            val ptx = signTransaction(tx, me)

            // Initialising session with other party
            //(((tx.outputs as java.util.ArrayList<*>)[0] as TransactionState).data as TodoState).participants
            val flows = ArrayList<FlowSession>()
            participants
                    .filter {!(it == me || it == notary)}
                    .forEach { flows.add(initiateFlow(it as Party)) }

            val stx = collectSignatures(ptx, flows)

            return finality(stx, flows)
        }

        @Suspendable
        private fun finality(stx: SignedTransaction, counterpartySession: List<FlowSession>) =
                subFlow(FinalityFlow(transaction = stx, sessions = counterpartySession, progressTracker = FINALISING.childProgressTracker()))

        @Suspendable
        private fun collectSignatures(ptx: SignedTransaction, counterpartySession: List<FlowSession>): SignedTransaction {
            return subFlow(CollectSignaturesFlow(
                    partiallySignedTx = ptx,
                    sessionsToCollectFrom = counterpartySession,
                    progressTracker = COLLECTING_SIGNATURES.childProgressTracker()
            ))
        }

        private fun signTransaction(tx: TransactionBuilder, me: Party): SignedTransaction {
            val myKeysToSign = listOf(me.owningKey)
            return serviceHub.signInitialTransaction(tx, myKeysToSign)
        }

        private fun getTx(notary: Party, todoStateStateRef: StateAndRef<TodoState>, participants: MutableList<AbstractParty>): TransactionBuilder {
            val txBuilder = TransactionBuilder(notary)
            txBuilder.addInputState(todoStateStateRef)
            val todoState = todoStateStateRef.state.data

//            val participants = todoState.participants.filter { it.owningKey == participant.owningKey }.toMutableList()
//            if (participants.isEmpty()) {
//                participants.addAll(todoState.participants)
//                participants.add(participant)
//            }
            val todoStateOutput = todoState.copy(participants = participants)
            txBuilder.addOutputState(todoStateOutput)

            val command = getCommand(todoStateOutput)
            txBuilder.addCommand(command)

            progressTracker.currentStep = VERIFY_TRANSACTION
            txBuilder.verify(serviceHub)
            return txBuilder
        }

        private fun getTodo(linearId: UniqueIdentifier): StateAndRef<TodoState> {
            val criteria = QueryCriteria.LinearStateQueryCriteria(linearId = listOf(linearId)).and(QueryCriteria.VaultQueryCriteria(status = Vault.StateStatus.UNCONSUMED))
            val todoStateAndRef = serviceHub.vaultService.queryBy(TodoState::class.java, criteria).states.firstOrNull()
                    ?: throw IllegalArgumentException("TodoState with linearId $linearId not found. ")
            return todoStateAndRef
        }

//        private fun getCommand(me: Party, participant: Party) =
//                Command(TodoContract.Commands.Invite(), listOf(me.owningKey, participant.owningKey))

        private fun getCommand(todoState: TodoState) =
                Command(TodoContract.Commands.Invite(), todoState.participants.map { it.owningKey })

        private fun getMe() = this.ourIdentity

        private fun getNotary() = serviceHub.networkMapCache.notaryIdentities.single()
    }


    @InitiatedBy(AddParticipantsSender::class)
    class AddParticipantsResponder(val counterpartySession: FlowSession) : FlowLogic<Unit>() {
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