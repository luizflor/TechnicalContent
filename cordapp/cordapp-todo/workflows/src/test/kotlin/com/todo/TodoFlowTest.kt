package com.todo

import com.todo.states.TodoState
import net.corda.core.utilities.getOrThrow
import org.junit.Test
import kotlin.test.assertEquals

class TodoFlowTest : FlowTests() {

    @Test
    fun `Create - ok`() {
        val sender = createTask("buy milk")
        val senderResult =  sender.getOrThrow()
        network.waitQuiescent()
        val todoState = senderResult.tx.outputsOfType<TodoState>().single()
        logger.info(todoState.toString())
    }

    @Test
    fun `Complete - ok`() {
        val senderCreate = createTask("buy milk")
        val senderCreateResult =  senderCreate.getOrThrow()
        network.waitQuiescent()
        val todoState = senderCreateResult.tx.outputsOfType<TodoState>().single()
        logger.info(todoState.toString())

        val senderComplete = completeTask(todoState.linearId)
        val senderCompleteResult = senderComplete.getOrThrow()
        network.waitQuiescent()

        val todoStateCompleted = senderCompleteResult.tx.outputsOfType<TodoState>().toList()
        assertEquals(0,todoStateCompleted.size)
    }

    @Test
    fun `Cancel - ok`() {
        val senderCreate = createTask("buy milk")
        val senderCreateResult =  senderCreate.getOrThrow()
        network.waitQuiescent()
        val todoState = senderCreateResult.tx.outputsOfType<TodoState>().single()
        logger.info(todoState.toString())

        val senderCancel= cancelTask(todoState.linearId)
        val senderCancelResult = senderCancel.getOrThrow()
        network.waitQuiescent()

        val todoStateCanceled= senderCancelResult.tx.outputsOfType<TodoState>().toList()
        assertEquals(1,todoStateCanceled.size)

        val senderUnCancel= cancelTask(todoState.linearId, false)
        val senderUnCancelResult = senderUnCancel.getOrThrow()
        network.waitQuiescent()

        val todoStateCanceled2= senderUnCancelResult.tx.outputsOfType<TodoState>().toList()
        assertEquals(1,todoStateCanceled2.size)
    }
}