package com.todo.webserver.model

import com.todo.states.TodoState
import net.corda.core.contracts.StateRef
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.Party
import net.corda.core.node.services.Vault


data class TodoState(
        val index: Int,
        val owner: String,
        val task: String,
        val status: String,
        val entryDateTime: String,
        val linearId: UniqueIdentifier,
        val participants: List<String>,
        val participantsCompleted: List<String>,
        val transactionId: String,
        val stateRef: String,
        val metadata: StateMetadata?
) {
    constructor():this( index = 0, owner="",task = "",status = "",entryDateTime = "",linearId = UniqueIdentifier(),participants = listOf(""), metadata= StateMetadata(), participantsCompleted = listOf(""), transactionId="", stateRef = "")
    constructor(index: Int, todo: TodoState, transactionId:String, metadata: Vault.StateMetadata?, ref: StateRef?):this(
            index = index,
            owner=todo.owner.name.toString(),
            task = todo.task,
            status = todo.status.toString(),
            entryDateTime = todo.entryDateTime.toString(),
            linearId = todo.linearId,
            participants = todo.participants.map { (it as Party).name.toString() },
            participantsCompleted = todo.participantsCompleted.map { (it as Party).name.toString() },
            metadata = metadata?.let { StateMetadata(it) },
            transactionId=transactionId,
            stateRef = ref.toString()
    )

    override fun toString(): String {
        return "owner: $owner task=: $task status: $status entryDateTime: $entryDateTime linearId: $linearId participants: $participants participantsCompleted: $participantsCompleted"
    }
}

data class StateMetadata(val consumedTime: String, val lockId: String, val lockUpdateTime: String, val recordedTime: String, val status: String, val ref: String?) {
    constructor(): this( consumedTime = "",status = "",ref = null,lockId = "",lockUpdateTime = "",recordedTime = "")
    constructor( metadata: Vault.StateMetadata): this(
            recordedTime = metadata.recordedTime.toString(),
            lockUpdateTime = metadata.lockUpdateTime.toString(),
            lockId = metadata.lockId.toString(),
            ref = metadata.ref.toString(),
            status = metadata.status.toString(),
            consumedTime = metadata.consumedTime.toString())

    override fun toString(): String {
        return "consumedTime: $consumedTime  lockId: $lockId lockUpdateTime: $lockUpdateTime recordedTime: $recordedTime status: $status ref: $ref"
    }
}