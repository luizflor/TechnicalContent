package schema

import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

object CountSchema

object CountSchemaV1: MappedSchema(
        schemaFamily = CountSchema.javaClass,
        version = 1,
        mappedTypes = listOf(CountSchemaV1.PersistentCount::class.java)
){
    override val migrationResource = "count-schema.changelog-master"

    @Entity
    @Table(name="count_state")
    class PersistentCount(
            @Column(name = "partnerA", length = 255, nullable = false)
            val partnerA: String,

            @Column(name = "partnerB", length = 255, nullable = false)
            val partnerB: String,

            @Column(name="count", nullable = false)
            val count: Int,

            @Column(name = "entryDateTime", nullable = false)
            var entryDateTime: Instant,

            @Column(name = "linear_id", length = 64, nullable = false)
            var linearId: String
    ): PersistentState() {
        constructor():this("","",0, Instant.now(),"")
    }
}