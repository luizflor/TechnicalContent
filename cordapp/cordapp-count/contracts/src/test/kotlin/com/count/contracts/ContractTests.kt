package com.count.contracts

import net.corda.testing.node.ledger
import org.junit.Test

class ContractTests: CountContractTestBase() {

    @Test
    fun `count start OK`() {
        ledgerServices.ledger {
            transaction {
                output(CountContract.ID, count_State)
                command(signers = listOf(partnerA.publicKey, partnerB.publicKey), commandData = CountContract.Commands.Start())
                verifies()
            }
        }
    }

    @Test
    fun `count add OK`() {
        ledgerServices.ledger {
            transaction {
                input(CountContract.ID, count_State)
                output(CountContract.ID, count_State.copy(Count = count_State.Count + 1))
                command(signers = listOf(partnerA.publicKey, partnerB.publicKey), commandData = CountContract.Commands.Add())
                verifies()
            }
        }
    }

    @Test
    fun `count unknow partner`() {
        ledgerServices.ledger {
            transaction {
                input(CountContract.ID, count_State)
                output(CountContract.ID, count_State.copy(Count = count_State.Count + 1, partnerA = partnerC.party))
                command(signers = listOf(partnerA.publicKey, partnerB.publicKey), commandData = CountContract.Commands.Add())
                failsWith("Output partnerA O=Bank A CorpC, L=San Francisco, C=US must be one either O=Bank A CorpA, L=San Francisco, C=US or O=Bank A CorpB, L=San Francisco, C=US")
            }
        }
    }
}