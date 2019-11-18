package com.todo.contracts

import com.todo.states.TodoState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.CordaX500Name
import net.corda.core.identity.Party
import net.corda.testing.common.internal.testNetworkParameters
import net.corda.testing.core.TestIdentity
import net.corda.testing.node.MockServices
import org.junit.After
import org.junit.Before


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
    val ATest = TestIdentity(CordaX500Name("Bank A Corp", "San Francisco", "US"))
    val BTest = TestIdentity(CordaX500Name("Bank B Corp", "Dallas", "US"))
    val CTest = TestIdentity(CordaX500Name("Bank C Corp", "New York", "US"))
    val linearId = UniqueIdentifier()
    lateinit var PartyA: Party
    lateinit var PartyB: Party
    lateinit var PartyC: Party


    lateinit var todo_State: TodoState

    @Before
    fun setup() {
        PartyA = ATest.party
        PartyB = BTest.party
        PartyC = CTest.party

        todo_State = TodoState(owner=PartyA,task = "Buy milk", linearId = linearId)
    }

    @After()
    fun  tearDown(){

    }
}