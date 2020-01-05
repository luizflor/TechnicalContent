package com.count.flows

import co.paralleluniverse.fibers.Suspendable
import com.count.contracts.CountContract
import com.count.states.CountState
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

/**
 * This flow add 1 to count state by consuming its previous state
 */
object AddFlow {

    @CordaSerializable
    data class Info(val Id: UniqueIdentifier, val participant: Party)

    @InitiatingFlow
    @StartableByRPC
    class AddSender(val Id: UniqueIdentifier, val participant: Party) : FlowLogic<SignedTransaction>() {
        constructor(info: Info): this(Id=info.Id, participant = info.participant)
        override val progressTracker = tracker()

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

        @Suspendable
        override fun call(): SignedTransaction {
            var buildTransaction = transaction()
            val transaction: TransactionBuilder = buildTransaction.first
            val signedTransaction: SignedTransaction = verifyAndSign(transaction)

            val inputState = buildTransaction.second
            val inputParticipants = inputState.participants - this.ourIdentity - getNotary()
            val outputParticipants = (transaction.outputStates().single().data as CountState).participants - this.ourIdentity - getNotary()
            val participants = outputParticipants + inputParticipants
            val sessions = ArrayList<FlowSession>()

            // There is an issue with the following code https://r3-cev.atlassian.net/browse/CORDA-3485
            participants.forEach { sessions.add(initiateFlow(it as Party)) }
            val stx = collectSignatures(signedTransaction, sessions)

            return finality(stx, sessions)

        }

        private fun transaction() : Pair<TransactionBuilder, CountState> {
            progressTracker.currentStep = BUILD_TRANSACTION
            val notary = serviceHub.networkMapCache.notaryIdentities.single()
            val txBuilder = TransactionBuilder(notary)

            val todoStateRef = getCountStateAndRef(this.Id)
            txBuilder.addInputState(todoStateRef)

            val todoState=  todoStateRef.state.data
            val todoStateOutput = todoState.copy(participants = listOf( this.participant, this.ourIdentity), Count = todoState.Count + 1, partnerA = this.ourIdentity, partnerB = this.participant)
            txBuilder.addOutputState(todoStateOutput)

            val signers = todoState.participants + this.participant
            val command = Command(CountContract.Commands.Add(), signers.map { it.owningKey })
            txBuilder.addCommand(command)

            progressTracker.currentStep = VERIFY_TRANSACTION
            txBuilder.verify(serviceHub)
            return Pair(txBuilder,todoState)
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
                    progressTracker = COLLECTING_SIGNATURES.childProgressTracker()
            ))
        }


        /**
         * We want to create a situation for input states have already been used as input states in other transactions
         * That is why the criteria is for Vault.StateStatus.ALL and use the last state
         */
        private fun getCountStateAndRef(linearId: UniqueIdentifier): StateAndRef<CountState> {
            val criteria = QueryCriteria.LinearStateQueryCriteria(linearId = listOf(linearId), status = Vault.StateStatus.ALL)
//            val criteria = QueryCriteria.LinearStateQueryCriteria(linearId = listOf(linearId)).and(QueryCriteria.VaultQueryCriteria(status = Vault.StateStatus.UNCONSUMED))
            val states = serviceHub.vaultService.queryBy(CountState::class.java, criteria).states

            val countStateAndRef = states.lastOrNull() ?: throw IllegalArgumentException("CountState with linearId $linearId not found. ")
//            val countStateAndRef = states.firstOrNull() ?: throw IllegalArgumentException("CountState with linearId $linearId not found. ")
            return countStateAndRef
        }

        private fun getNotary() = serviceHub.networkMapCache.notaryIdentities.single()

    }

    @InitiatedBy(AddSender::class)
    class AddResponder(val counterpartySession: FlowSession) : FlowLogic<Unit>() {
        @Suspendable
        override fun call() {
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

