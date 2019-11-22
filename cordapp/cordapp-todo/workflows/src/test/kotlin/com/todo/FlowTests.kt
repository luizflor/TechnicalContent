package com.todo

import com.todo.flows.*
//import com.todo.flows.Responder
import net.corda.core.concurrent.CordaFuture
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.CordaX500Name
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.testing.common.internal.testNetworkParameters
import net.corda.testing.node.MockNetwork
import net.corda.testing.node.MockNetworkNotarySpec
import net.corda.testing.node.MockNetworkParameters
import net.corda.testing.node.TestCordapp
import org.checkerframework.common.aliasing.qual.Unique
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Note:
 * Use threadPerNode = true for MockNetwork to improve performance;
 * Set threadPerNode = false to enable debugging into flows.
 * Use waitQuiescent() which is the preferred way now instead of runNetwork().
 * Important:
 * Use threadPerNode = true with waitQuiescent() and
 * threadPerNode = false with runNetwork()
 */
abstract class FlowTests {
    val logger = LoggerFactory.getLogger(FlowTests::class.java)
    val notaryName = CordaX500Name.parse("O=Notary,L=London,C=GB")
    protected val network = MockNetwork(MockNetworkParameters(cordappsForAllNodes = listOf(
        TestCordapp.findCordapp("com.todo.contracts"),
        TestCordapp.findCordapp("com.todo.flows")
    ),    notarySpecs = listOf(MockNetworkNotarySpec(name = notaryName, validating = false)),
            networkParameters = testNetworkParameters(minimumPlatformVersion = 4),threadPerNode = true))
    protected val a = network.createNode()
    protected val b = network.createNode()
    protected val c = network.createNode()

    @Before
    fun setup(){ }

    @After
    fun tearDown() = network.stopNodes()

    protected fun createTask(task: String): CordaFuture<SignedTransaction> {
        val info = CreateFlow.Info(task = task)
        val sender = CreateFlow.CreateSender(info)

        return a.startFlow(sender)
    }

    protected fun completeTask(taskId: UniqueIdentifier): CordaFuture<SignedTransaction> {
        val info = CompleteFlow.Info(taskId = taskId)
        val sender = CompleteFlow.CompleteSender(info)

        return a.startFlow(sender)
    }

    protected fun cancelTask(taskId: UniqueIdentifier, isCancel: Boolean = true): CordaFuture<SignedTransaction> {
        val info = CancelFlow.Info(taskId = taskId, isCancel = isCancel)
        val sender = CancelFlow.CancelSender(info)

        return a.startFlow(sender)
    }

    protected fun addParticipantTask(taskId: UniqueIdentifier, participant: Party): CordaFuture<SignedTransaction> {
        val info = AddParticipantsFlow.Info(taskId = taskId, participant = participant)
        val sender = AddParticipantsFlow.AddParticipantsSender(info)

        return a.startFlow(sender)
    }
    protected fun removeParticipantTask(taskId: UniqueIdentifier): CordaFuture<SignedTransaction> {
        val info = RemoveParticipantsFlow.Info(taskId = taskId)
        val sender = RemoveParticipantsFlow.RemoveParticipantsSender(info)

        return b.startFlow(sender)
    }
}