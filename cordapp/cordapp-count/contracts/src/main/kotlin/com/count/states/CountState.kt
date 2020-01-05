package com.count.states

import com.count.contracts.CountContract
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import net.corda.core.schemas.QueryableState
import schema.CountSchemaV1
import java.time.Instant

// *********
// * State *
// *********
@BelongsToContract(CountContract::class)
data class CountState(
        val partnerA: Party,
        val partnerB: Party,
        val Count: Int =0,
        val entryDateTime: Instant = Instant.now(),
        override val linearId: UniqueIdentifier = UniqueIdentifier(),
        override val participants: List<AbstractParty> = listOf(partnerA, partnerB)
) : LinearState, QueryableState {
    override fun generateMappedObject(schema: MappedSchema): PersistentState {
        return when (schema) {
            is CountSchemaV1 -> CountSchemaV1.PersistentCount(
                    partnerA = this.partnerA.name.toString().take(255),
                    partnerB = this.partnerB.name.toString().take(255),
                    linearId = this.linearId.id.toString(),
                    entryDateTime = this.entryDateTime,
                    count = this.Count
            )
            else -> throw IllegalArgumentException("Unrecognized schema $schema")
        }
    }

    override fun supportedSchemas(): Iterable<MappedSchema> = listOf(CountSchemaV1)
}
