package com.count

import com.count.states.CountState
import net.corda.core.flows.NotaryException
import net.corda.core.utilities.getOrThrow
import org.junit.Test
import kotlin.test.assertEquals


class CountFlowTest : FlowTests() {
    @Test
    fun `Start - ok`() {
        val start = startTask(a)
        val (startResult,_) = start.getOrThrow()
        network.waitQuiescent()
        val countState = startResult.tx.outputsOfType<CountState>().single()
        logger.info(countState.toString())

    }
    @Test
    fun `Add a-b - ok`() {
        // Start
        val start = startTask(a)
        val (startResult,_) = start.getOrThrow()
        network.waitQuiescent()
        val countStateStep1 = startResult.tx.outputsOfType<CountState>().single()
        logger.info(countStateStep1.toString())
        assertEquals(0,countStateStep1.Count)

        // Add
        val add = addTask(a, b, countStateStep1.linearId)
        val addResult = add.getOrThrow()
        network.waitQuiescent()
        val countStateStep2 = addResult.tx.outputsOfType<CountState>().single()
        logger.info(countStateStep2.toString())
        assertEquals(1,countStateStep2.Count)

    }

    @Test
    fun `Add a-b-c - ok`() {
        // Start
        val start = startTask(a)
        val (startResult,_) = start.getOrThrow()
        network.waitQuiescent()
        val countStateStep1 = startResult.tx.outputsOfType<CountState>().single()
        logger.info(countStateStep1.toString())
        assertEquals(0,countStateStep1.Count)

        // Add a-b
        val add = addTask(a, b, countStateStep1.linearId)
        val addResult = add.getOrThrow()
        network.waitQuiescent()
        val countStateStep2 = addResult.tx.outputsOfType<CountState>().single()
        logger.info(countStateStep2.toString())
        assertEquals(1,countStateStep2.Count)

        // Add b-c
        val add2 = addTask(b, c, countStateStep1.linearId)
        val addResult2 = add2.getOrThrow()
        network.waitQuiescent()
        val countStateStep3 = addResult2.tx.outputsOfType<CountState>().single()
        logger.info(countStateStep2.toString())
        assertEquals(2,countStateStep3.Count)

    }

    @Test(expected = NotaryException::class)
     fun `Add a-b-c a-b - throws Notary Exception`() {
        // Start
        val start = startTask(a)
        val (startResult,_) = start.getOrThrow()
        network.waitQuiescent()
        val countStateStep1 = startResult.tx.outputsOfType<CountState>().single()
        logger.info(countStateStep1.toString())
        assertEquals(0,countStateStep1.Count)

        // Add a-b
        val add = addTask(a, b, countStateStep1.linearId)
        val addResult = add.getOrThrow()
        network.waitQuiescent()
        val countStateStep2 = addResult.tx.outputsOfType<CountState>().single()
        logger.info(countStateStep2.toString())
        assertEquals(1,countStateStep2.Count)

        // Add b-c
        val add2 = addTask(b, c, countStateStep1.linearId)
        val addResult2 = add2.getOrThrow()
        network.waitQuiescent()
        val countStateStep3 = addResult2.tx.outputsOfType<CountState>().single()
        logger.info(countStateStep3.toString())
        assertEquals(2,countStateStep3.Count)

        // Add a-c - this will throw NotaryException - One or more input states or referenced states have already been used as input states in other transactions
        val add3 = addTask(a, c, countStateStep1.linearId)
        val addResult3 = add3.getOrThrow()
        network.waitQuiescent()
        val countStateStep4 = addResult3.tx.outputsOfType<CountState>().single()
        logger.info(countStateStep4.toString())
        assertEquals(3,countStateStep4.Count)

    }
}