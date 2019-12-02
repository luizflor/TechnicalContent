package com.todo

import com.todo.states.TodoState
import net.corda.core.utilities.getOrThrow
import net.corda.testing.core.singleIdentity
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
        // create task
        val senderCreate = createTask("buy milk")
        val senderCreateResult =  senderCreate.getOrThrow()
        network.waitQuiescent()
        val todoState = senderCreateResult.tx.outputsOfType<TodoState>().single()
        logger.info(todoState.toString())

        // complete task
        val senderComplete = completeTask(todoState.linearId, a)
        val senderCompleteResult = senderComplete.getOrThrow()
        network.waitQuiescent()

        val todoStateCompleted = senderCompleteResult.tx.outputsOfType<TodoState>().toList()
        assertEquals(1,todoStateCompleted.size)

        // complete task - now it should be consumed
        val senderComplete2 = completeTask(todoState.linearId, a)
        val senderCompleteResult2 = senderComplete2.getOrThrow()
        network.waitQuiescent()

        val todoStateCompleted2 = senderCompleteResult2.tx.outputsOfType<TodoState>().toList()
        assertEquals(0,todoStateCompleted2.size)
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

    @Test
    fun `Add Invitation - ok`() {
        val sender = createTask("buy milk")
        val senderResult =  sender.getOrThrow()
        network.waitQuiescent()
        val todoState = senderResult.tx.outputsOfType<TodoState>().single()
        logger.info(todoState.toString())

        val addParticipant = addParticipantTask(todoState.linearId, b.info.singleIdentity())
        val addParticipantResult = addParticipant.getOrThrow()
        network.waitQuiescent()

        val todoStateCompleted = addParticipantResult.tx.outputsOfType<TodoState>().single()
        assertEquals(2,todoStateCompleted.participants.size)

        val addParticipant2 = addParticipantTask(todoState.linearId, c.info.singleIdentity())
        val addParticipantResult2 = addParticipant2.getOrThrow()
        network.waitQuiescent()

        val todoStateCompleted2 = addParticipantResult2.tx.outputsOfType<TodoState>().single()
        assertEquals(3,todoStateCompleted2.participants.size)
    }

    @Test
    fun `Remove Invitation - ok`() {
        val sender = createTask("buy milk")
        val senderResult =  sender.getOrThrow()
        network.waitQuiescent()
        val todoState = senderResult.tx.outputsOfType<TodoState>().single()
        logger.info(todoState.toString())

        val addParticipant = addParticipantTask(todoState.linearId, b.info.singleIdentity())
        val addParticipantResult = addParticipant.getOrThrow()
        network.waitQuiescent()

        val todoStateCompleted = addParticipantResult.tx.outputsOfType<TodoState>().single()
        assertEquals(2,todoStateCompleted.participants.size)

        val removeParticipant = removeParticipantTask(todoState.linearId)
        val removeParticipantResult = removeParticipant.getOrThrow()
        network.waitQuiescent()

        val todoStateCompleted2 = removeParticipantResult.tx.outputsOfType<TodoState>().single()
        assertEquals(1,todoStateCompleted2.participants.size)
    }

    @Test
    fun `Complete After Invitation - ok`() {
        val sender = createTask("buy milk")
        val senderResult =  sender.getOrThrow()
        network.waitQuiescent()
        val todoState = senderResult.tx.outputsOfType<TodoState>().single()
        logger.info(todoState.toString())

        val addParticipant = addParticipantTask(todoState.linearId, b.info.singleIdentity())
        val addParticipantResult = addParticipant.getOrThrow()
        network.waitQuiescent()

        val todoStateParticipants = addParticipantResult.tx.outputsOfType<TodoState>().single()
        assertEquals(2,todoStateParticipants.participants.size)

        val addParticipant2 = addParticipantTask(todoState.linearId, c.info.singleIdentity())
        val addParticipantResult2 = addParticipant2.getOrThrow()
        network.waitQuiescent()

        val todoStateParticipants2 = addParticipantResult2.tx.outputsOfType<TodoState>().single()
        assertEquals(3,todoStateParticipants2.participants.size)

        // complete task by C
        val senderCompleteC = completeTask(todoState.linearId, c)
        val senderCompleteResultC = senderCompleteC.getOrThrow()
        network.waitQuiescent()

        val todoStateCompletedC = senderCompleteResultC.tx.outputsOfType<TodoState>().toList()
        assertEquals(1,todoStateCompletedC.first().participantsCompleted.size)

        // complete task by B
        val senderCompleteB = completeTask(todoState.linearId, b)
        val senderCompleteResultB = senderCompleteB.getOrThrow()
        network.waitQuiescent()

        val todoStateCompletedB = senderCompleteResultB.tx.outputsOfType<TodoState>().toList()
        assertEquals(2,todoStateCompletedB.first().participantsCompleted.size)

        // complete task by A
        val senderCompleteA = completeTask(todoState.linearId, a)
        val senderCompleteResultA = senderCompleteA.getOrThrow()
        network.waitQuiescent()

        val todoStateCompletedA = senderCompleteResultA.tx.outputsOfType<TodoState>().toList()
        assertEquals(3,todoStateCompletedA.first().participantsCompleted.size)

        // complete task by A Consume completetly
        val senderCompleteA2 = completeTask(todoState.linearId, a)
        val senderCompleteResultA2 = senderCompleteA2.getOrThrow()
        network.waitQuiescent()

        val todoStateCompletedA2 = senderCompleteResultA2.tx.outputsOfType<TodoState>().toList()
        assertEquals(0,todoStateCompletedA2.size)
    }
}