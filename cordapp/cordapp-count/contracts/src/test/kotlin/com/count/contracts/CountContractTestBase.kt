package com.count.contracts

import com.count.states.CountState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.crypto.internal.platformSecureRandom
import net.corda.core.identity.CordaX500Name
import net.corda.core.identity.Party
import net.corda.testing.common.internal.testNetworkParameters
import net.corda.testing.core.TestIdentity
import net.corda.testing.node.MockServices
import org.junit.After
import org.junit.Before
import java.security.KeyPair

abstract class CountContractTestBase {
    val megaCorp = TestIdentity(CordaX500Name("MegaCorp", "London", "GB"))
    val cordapps = listOf(
            "com.count.contracts"
    )

    val ledgerServices = MockServices(
            cordappPackages = cordapps,
            firstIdentity = megaCorp,
            networkParameters = testNetworkParameters(
                    minimumPlatformVersion = 4
            )
    )
    val partnerA = TestIdentity(CordaX500Name("Bank A CorpA", "San Francisco", "US"))
    val partnerB = TestIdentity(CordaX500Name("Bank A CorpB", "San Francisco", "US"))
    val partnerC = TestIdentity(CordaX500Name("Bank A CorpC", "San Francisco", "US"))
    val linearId = UniqueIdentifier()
//    lateinit var me_KeyPair: KeyPair
//    lateinit var me: Party
    lateinit var count_State: CountState

    @Before
    fun setup() {
//        me = partnerA.party
//        me_KeyPair = partnerA.keyPair
        count_State = CountState(partnerA= partnerA.party, partnerB = partnerB.party)
    }

    @After()
    fun  tearDown(){

    }
}