package com.todo

import com.todo.flows.CancelFlow
import com.todo.flows.CompleteFlow
import com.todo.flows.CreateFlow
//import com.todo.flows.Responder
import net.corda.core.concurrent.CordaFuture
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.CordaX500Name
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
    private val a = network.createNode()
    private val b = network.createNode()

    init {
//        listOf(a, b).forEach {
//            it.registerInitiatedFlow(Responder::class.java)
//        }
    }

    @Before
    fun setup(){ }//= network.runNetwork()

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
}