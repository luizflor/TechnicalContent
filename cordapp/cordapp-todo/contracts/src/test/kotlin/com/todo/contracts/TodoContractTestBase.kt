package com.todo.contracts

import com.todo.states.TodoState
import net.corda.core.concurrent.CordaFuture
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.CordaX500Name
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.testing.common.internal.testNetworkParameters
import net.corda.testing.core.TestIdentity
import net.corda.testing.node.MockServices
import org.junit.After
import org.junit.Before
import java.security.KeyPair


abstract class TodoContractTestBase {
    val megaCorp = TestIdentity(CordaX500Name("MegaCorp", "London", "GB"))
    val cordapps = listOf(
            "com.todo.contracts"
    )

    val ledgerServices = MockServices(
            cordappPackages = cordapps,
            firstIdentity = megaCorp,
            networkParameters = testNetworkParameters(
                    minimumPlatformVersion = 4
            )
    )
    val meTest = TestIdentity(CordaX500Name("Bank A Corp", "San Francisco", "US"))
    val linearId = UniqueIdentifier()
    lateinit var me_KeyPair: KeyPair
    lateinit var me: Party
    lateinit var todo_State: TodoState

    @Before
    fun setup() {
        me = meTest.party
        me_KeyPair = meTest.keyPair
        todo_State = TodoState(me=me,task = "Buy milk")
    }

    @After()
    fun  tearDown(){

    }



}