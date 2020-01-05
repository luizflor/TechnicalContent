package com.count

import com.count.flows.AddFlow
import com.count.flows.StartFlow
import net.corda.core.concurrent.CordaFuture
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.CordaX500Name
import net.corda.core.transactions.SignedTransaction
import net.corda.testing.common.internal.testNetworkParameters
import net.corda.testing.node.*
import org.junit.After
import org.junit.Before

import org.slf4j.LoggerFactory

abstract class FlowTests {
    val logger = LoggerFactory.getLogger(this.javaClass)
    val notaryName = CordaX500Name.parse("O=Notary,L=London,C=GB")
    protected val network = MockNetwork(MockNetworkParameters(cordappsForAllNodes = listOf(
        TestCordapp.findCordapp("com.count.contracts"),
        TestCordapp.findCordapp("com.count.flows")
    ), notarySpecs = listOf(MockNetworkNotarySpec(name = notaryName, validating = false)),
            networkParameters = testNetworkParameters(minimumPlatformVersion = 4),threadPerNode = true))

    protected val a = network.createNode()
    protected val b = network.createNode()
    protected val c = network.createNode()

    @Before
    fun setup() {}

    @After
    fun tearDown() = network.stopNodes()

   protected fun startTask(node: StartedMockNode): CordaFuture<Pair<SignedTransaction, UniqueIdentifier>> {
       val start = StartFlow.StartSender(b.info.legalIdentities.first())
       return node.startFlow(start)
   }
    protected fun addTask(node: StartedMockNode, participantNode: StartedMockNode, id: UniqueIdentifier): CordaFuture<SignedTransaction> {
        val info = AddFlow.Info(Id=id, participant = participantNode.info.legalIdentities.first())
       val add = AddFlow.AddSender(info)
       return node.startFlow(add)
   }
}