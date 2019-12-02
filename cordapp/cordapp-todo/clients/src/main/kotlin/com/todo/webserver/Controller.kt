package com.todo.webserver

import com.todo.flows.*
import com.todo.states.TodoState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.messaging.vaultQueryBy
import net.corda.core.node.services.Vault
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.utilities.getOrThrow
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Define your API endpoints here.
 */
@RestController
@RequestMapping("/") // The paths for HTTP requests are relative to this base path.
class Controller(val rpc: NodeRPCConnection) {

    companion object {
        private val logger = LoggerFactory.getLogger(RestController::class.java)
    }

    private val proxy = rpc.proxy

    @GetMapping( "/Todoendpoint", produces = arrayOf("text/plain"))
    private fun Todoendpoint(): String {
        return "Define an endpoint here."
    }

    @GetMapping( "/me", produces = arrayOf("text/plain"))
    private fun me(): String {
        return proxy.nodeInfo().legalIdentities[0].name.toString()
    }

    @GetMapping(  "/createTask", produces = arrayOf("application/json"))
    private fun CreateTask(@RequestParam("task" )task: String): ResponseEntity<Any> {
        logger.info("/CreateTask?task=$task")
        val info = CreateFlow.Info(task)
        val flow = rpc.proxy.startFlowDynamic(CreateFlow.CreateSender::class.java,info)
        val result = flow.returnValue.getOrThrow()
        val todoState = result.tx.outputsOfType<TodoState>().single()
        val todo = com.todo.webserver.model.TodoState(index=0, todo = todoState, metadata = null,transactionId = result.id.toString(), ref = null)
        return ResponseEntity.ok().body(todo)
    }

    @GetMapping("/cancelTask", produces = arrayOf("application/json"))
    private fun CancelTask(@RequestParam("taskId" )taskId: String, @RequestParam("isCancel") isCancel: Boolean): ResponseEntity<Any> {
        logger.info("/CancelTask?taskId?$taskId&isCancel=$isCancel")
        val info = CancelFlow.Info(taskId = UniqueIdentifier.fromString(taskId) ,isCancel =  isCancel)
        val flow = rpc.proxy.startFlowDynamic(CancelFlow.CancelSender::class.java,info)
        val result = flow.returnValue.getOrThrow()
        val todoState = result.tx.outputsOfType<TodoState>().single()
        val input = result.tx.inputs.single()
        val todo = com.todo.webserver.model.TodoState(index=0,todo = todoState, metadata = null, transactionId = result.id.toString(), ref = input)
        return ResponseEntity.ok().body(todo)
    }

    @GetMapping( "/completeTask", produces = arrayOf("application/json"))
    private fun CompleteTask(@RequestParam("taskId" )taskId: String): ResponseEntity<Any> {
        logger.info("/CompleteTask?taskId=$taskId")
        val info = CompleteFlow.Info(taskId = UniqueIdentifier.fromString(taskId))
        val flow = rpc.proxy.startFlowDynamic(CompleteFlow.CompleteSender::class.java, info)
        val result = flow.returnValue.getOrThrow()
        val todoStateList = result.tx.outputsOfType<TodoState>()
        val todo: com.todo.webserver.model.TodoState
        if (todoStateList.isEmpty()) {
            todo = com.todo.webserver.model.TodoState()
        } else {
            val todoState = todoStateList.single()
            val input = result.tx.inputs.single()
            todo = com.todo.webserver.model.TodoState(index = 0, todo = todoState, metadata = null, transactionId = result.id.toString(), ref = input)
        }
        return ResponseEntity.ok().body(todo)
    }

    @GetMapping( "/addParticipant", produces = arrayOf("application/json"))
    private fun AddParticipant(@RequestParam("taskId" )taskId: String, @RequestParam("participant")participant: String): ResponseEntity<Any> {
        logger.info("/addParticipanrt?taskId=$taskId&participant=$participant")
        val matches = proxy.partiesFromName(participant,true)
        val to = when {
            matches.isEmpty() -> throw IllegalArgumentException("Participant is not in any nodes on the network")
            matches.size > 1 -> throw IllegalArgumentException("Particpant in multiple nodes on the network")
            else -> matches.single()
        }
        val info = AddParticipantsFlow.Info(taskId = UniqueIdentifier.fromString(taskId),participant = to)
        val flow = rpc.proxy.startFlowDynamic(AddParticipantsFlow.AddParticipantsSender::class.java,info)
        val result = flow.returnValue.getOrThrow()
        val todoState = result.tx.outputsOfType<TodoState>().single()
        val input = result.tx.inputs.single()
        val todo = com.todo.webserver.model.TodoState(index=0,todo = todoState, metadata = null,transactionId = result.id.toString(), ref = input)
        return ResponseEntity.ok().body(todo)
    }

    @GetMapping( "/removeParticipant", produces = arrayOf("application/json"))
    private fun RemoveParticipant(@RequestParam("taskId" )taskId: String): ResponseEntity<Any> {
        logger.info("/removeParticipant?taskId=$taskId")
        val info = RemoveParticipantsFlow.Info(taskId = UniqueIdentifier.fromString(taskId))
        val flow = rpc.proxy.startFlowDynamic(RemoveParticipantsFlow.RemoveParticipantsSender::class.java,info)
        val result = flow.returnValue.getOrThrow()
        val todoState = result.tx.outputsOfType<TodoState>().single()
        val input = result.tx.inputs.single()
        val todo = com.todo.webserver.model.TodoState(index=0,todo = todoState, metadata = null,transactionId = result.id.toString(), ref = input)
        return ResponseEntity.ok().body(todo)
    }

    @GetMapping( "/task/{id}", produces = arrayOf("application/json"))
    private fun GetByTaskId(@PathVariable("id" )id: String): ResponseEntity<Any> {
        logger.info("/GetByTaskId/$id")

        val linearId = UniqueIdentifier.fromString(id)
        val criteria = QueryCriteria.LinearStateQueryCriteria(uuid = listOf(linearId.id), status = Vault.StateStatus.ALL)
        val todoStates =  rpc.proxy.vaultQueryBy<TodoState>(criteria= criteria)
        val todos  = mutableListOf<com.todo.webserver.model.TodoState>()
        var index = 0
        todoStates.states.forEach {
            val metadata = todoStates.statesMetadata[index++]
            val todo = com.todo.webserver.model.TodoState(index = index, todo = it.state.data, metadata = metadata, transactionId = metadata.ref.txhash.toString(), ref = null)
            logger.info("consumedTime: ${metadata.consumedTime}  lockId: ${metadata.lockId} lockUpdateTime: ${metadata.lockUpdateTime} recordedTime: ${metadata.recordedTime} status: ${metadata.status} ref: ${metadata.ref}")
            todos.add(todo)
        }
        return ResponseEntity.ok().body(todos)
    }
    @GetMapping( "/task", produces = arrayOf("application/json"))
    private fun GetTasks(): ResponseEntity<Any> {
        logger.info("/GetTasks")

        val criteria = QueryCriteria.LinearStateQueryCriteria( status = Vault.StateStatus.UNCONSUMED)
        val todoStates =  rpc.proxy.vaultQueryBy<TodoState>(criteria= criteria)
        val todos  = mutableListOf<com.todo.webserver.model.TodoState>()
        var index = 0
        todoStates.states.forEach {
            val metadata = todoStates.statesMetadata[index++]
            val todo = com.todo.webserver.model.TodoState(index = index, todo = it.state.data, metadata = metadata, transactionId = metadata.ref.txhash.toString(), ref = null)
            logger.info("consumedTime: ${metadata.consumedTime}  lockId: ${metadata.lockId} lockUpdateTime: ${metadata.lockUpdateTime} recordedTime: ${metadata.recordedTime} status: ${metadata.status} ref: ${metadata.ref}")
            todos.add(todo)
        }
        return ResponseEntity.ok().body(todos)
    }
}