package com.count.flows

import co.paralleluniverse.fibers.Suspendable
import com.count.contracts.CountContract
import com.count.states.CountState
import net.corda.core.contracts.Command
import net.corda.core.contracts.StateAndContract
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.node.StatesToRecord
import net.corda.core.serialization.CordaSerializable
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

// *********
// * Flows *
// *********
object StartFlow {

    @CordaSerializable
    data class Info(val participant: Party)

    @InitiatingFlow
    @StartableByRPC
    class StartSender(val participant: Party) : FlowLogic<Pair<SignedTransaction, UniqueIdentifier>>() {
        constructor(info:Info) : this(info.participant)
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
                   COLLECTING_SIGNATURES,
                    FINALISING
            )
        }

        @Suspendable
        override fun call(): Pair<SignedTransaction, UniqueIdentifier> {
            val notary = getNotary()

            val startCommand = getCommand()

            val countState = getCountState()

            progressTracker.currentStep = BUILD_TRANSACTION
            val tx = buildTransaction(notary, countState, startCommand)

            progressTracker.currentStep = SIGN_TRANSACTION
            val ptx = signTransaction(tx)

            val participantSession = initiateFlow(participant)

            val stx = collectSignatures(ptx, listOf(participantSession))

            return Pair(finality(stx, listOf(participantSession)), countState.linearId)

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

        private fun signTransaction(tx: TransactionBuilder): SignedTransaction {
            return serviceHub.signInitialTransaction(tx)
        }

        private fun buildTransaction(notary: Party, countState: CountState, createCommand: Command<CountContract.Commands.Start>): TransactionBuilder {
            val txBuilder = TransactionBuilder(notary)
            txBuilder.withItems(StateAndContract(countState, CountContract.ID), createCommand)

            progressTracker.currentStep = VERIFY_TRANSACTION
            txBuilder.verify(serviceHub)
            return txBuilder
        }

        private fun getCountState() = CountState(partnerA = this.ourIdentity, partnerB = this.participant)

        private fun getCommand() =
                Command(CountContract.Commands.Start(), signers =  listOf(this.ourIdentity.owningKey,  this.participant.owningKey))

        private fun getMe() = this.ourIdentity

        private fun getNotary() = serviceHub.networkMapCache.notaryIdentities.single()

    }

    @InitiatedBy(StartSender::class)
    class StartResponder(val counterpartySession: FlowSession) : FlowLogic<Unit>() {
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

