package com.todo.states

import com.todo.contracts.TodoContract
import com.todo.schema.TodoSchemaV1
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import net.corda.core.schemas.QueryableState
import net.corda.core.serialization.CordaSerializable
import java.time.Instant

@CordaSerializable
enum class TodoStatus {
    New,
    Canceled,
    Completed;

    override fun toString(): String {
        return super.toString().toUpperCase()
    }
}

// *********
// * State *
// *********
@BelongsToContract(TodoContract::class)
@CordaSerializable
data class TodoState(
        val owner: Party,
        val task: String,
        val status: TodoStatus = TodoStatus.New,
        val entryDateTime: Instant = Instant.now(),
        val participantsCommpleted: MutableList<AbstractParty> = mutableListOf<AbstractParty>(),
        override val linearId: UniqueIdentifier = UniqueIdentifier(),
        override val participants: MutableList<AbstractParty> = mutableListOf<AbstractParty>(owner)
) : LinearState, QueryableState {

    override fun generateMappedObject(schema: MappedSchema): PersistentState {
        return when (schema) {
            is TodoSchemaV1 -> TodoSchemaV1.PersistentTodo(
                    me = this.owner.name.toString().take(255),
                    task = this.task.take(255),
                    linearId = this.linearId.id.toString(),
                    entryDateTime = this.entryDateTime,
                    status = this.status.toString()

            )
            else -> throw IllegalArgumentException("Unrecognized schema $schema")
        }
    }

    override fun supportedSchemas(): Iterable<MappedSchema>  = listOf(TodoSchemaV1)

    override fun toString() = "${owner.name.toString()} - task: $task status: $status linearId: $linearId entryDateTime: $entryDateTime"
}
