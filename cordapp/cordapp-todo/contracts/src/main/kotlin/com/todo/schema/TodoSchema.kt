package com.todo.schema

import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

object TodoSchema

object TodoSchemaV1: MappedSchema(
        schemaFamily = TodoSchema.javaClass,
        version = 1,
        mappedTypes = listOf(
                PersistentTodo::class.java
        )) {

    override val migrationResource = "todo-schema.changelog-master"

    @Entity
    @Table(name = "todo_state")
    class PersistentTodo(
            @Column(name = "party", length = 255, nullable = false)
            val me: String,

            @Column(name = "task", length = 255, nullable = false)
            val task: String,

            @Column(name = "status", length = 50, nullable = false)
            val status: String,

            @Column(name = "entryDateTime", nullable = false)
            var entryDateTime: Instant,

            @Column(name = "linear_id", length = 64, nullable = false)
            var linearId: String
    ) : PersistentState() {
        constructor() : this("", "", "", Instant.now(),"")
    }
}