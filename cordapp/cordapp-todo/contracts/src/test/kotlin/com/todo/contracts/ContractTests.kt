package com.todo.contracts

import com.todo.states.TodoStatus
import net.corda.testing.node.ledger
import org.junit.Test

class ContractTests: TodoContractTestBase() {

    @Test
    fun `Create - ok`() {
        ledgerServices.ledger {
            transaction {
                output(TodoContract.TODO_CONTRACT_ID, todo_State)
                command(signer = PartyA.owningKey, commandData = TodoContract.Commands.Create())
                verifies()
            }
        }
    }
    @Test
    fun `Cancel - ok`() {
        ledgerServices.ledger {
            transaction {
                input(TodoContract.TODO_CONTRACT_ID, todo_State )
                output(TodoContract.TODO_CONTRACT_ID, todo_State.copy(status = TodoStatus.Canceled) )
                command(signer = PartyA.owningKey, commandData = TodoContract.Commands.Cancel())
                verifies()
            }
        }
    }
    @Test
    fun `Complete - ok`() {
        ledgerServices.ledger {
            transaction {
                input(TodoContract.TODO_CONTRACT_ID, todo_State.copy(participantsCompleted = mutableListOf(PartyA)) )
                command(signer = PartyA.owningKey, commandData = TodoContract.Commands.Complete())
                verifies()
            }
        }
    }
    @Test
    fun `Complete with more than 1 participant - ok`() {
        ledgerServices.ledger {
            transaction {
                input(TodoContract.TODO_CONTRACT_ID, todo_State.copy(participants= mutableListOf(PartyA, PartyB)))
                output(TodoContract.TODO_CONTRACT_ID, todo_State.copy(participantsCompleted= mutableListOf(PartyA), status = TodoStatus.Completed))
                command(signer = PartyA.owningKey, commandData = TodoContract.Commands.Complete())
                verifies()
            }
        }
    }

    @Test
    fun `Create - should not have an input`() {
        ledgerServices.ledger {
            transaction {
                input(TodoContract.TODO_CONTRACT_ID, todo_State)
                output(TodoContract.TODO_CONTRACT_ID, todo_State)
                command(signer = PartyA.owningKey, commandData = TodoContract.Commands.Create())
                failsWith(" Todo transaction should have no input.")
            }
        }
    }

    @Test
    fun `Cancel - should not have an output`() {
        ledgerServices.ledger {
            transaction {
                input(TodoContract.TODO_CONTRACT_ID, todo_State)
                command(signer = PartyA.owningKey, commandData = TodoContract.Commands.Cancel())
                failsWith("Todo transaction should have  output.")
            }
        }
    }

    @Test
    fun `Completed - Output status must be completed`() {
        ledgerServices.ledger {
            transaction {
                input(TodoContract.TODO_CONTRACT_ID, todo_State)
                output(TodoContract.TODO_CONTRACT_ID, todo_State)
                command(signer = PartyA.owningKey, commandData = TodoContract.Commands.Complete())
                failsWith("Output status must be completed")
            }
        }
    }


    @Test
    fun `Invite - ok`() {
        ledgerServices.ledger {
            transaction {
                input(TodoContract.TODO_CONTRACT_ID, todo_State.copy(status = TodoStatus.New) )
                output(TodoContract.TODO_CONTRACT_ID, todo_State.copy(participants = mutableListOf(PartyA, PartyB)) )
                command(listOf(PartyA.owningKey, PartyB.owningKey), commandData = TodoContract.Commands.Invite())
                verifies()
            }
        }
    }

    @Test
    fun `Invite a 3rd - ok`() {
        ledgerServices.ledger {
            transaction {
                input(TodoContract.TODO_CONTRACT_ID, todo_State.copy(status = TodoStatus.New, participants = mutableListOf(PartyA, PartyB)) )
                output(TodoContract.TODO_CONTRACT_ID, todo_State.copy(participants = mutableListOf(PartyA, PartyB, PartyC)) )
                command(listOf(PartyA.owningKey, PartyB.owningKey, PartyC.owningKey), commandData = TodoContract.Commands.Invite())
                verifies()
            }
        }
    }

    @Test
    fun `Uninvite - ok`() {
        ledgerServices.ledger {
            transaction {
                input(TodoContract.TODO_CONTRACT_ID, todo_State.copy(participants = mutableListOf(PartyA, PartyB)))
                output(TodoContract.TODO_CONTRACT_ID, todo_State.copy(participants = mutableListOf(PartyA)))
                command(listOf(PartyA.owningKey), commandData = TodoContract.Commands.Uninvite())
                verifies()
            }
        }
    }

    @Test
    fun `Uninvite - Owner must be present in the output`() {
        ledgerServices.ledger {
            transaction {
                input(TodoContract.TODO_CONTRACT_ID, todo_State.copy(participants = mutableListOf(PartyA, PartyB)) )
                output(TodoContract.TODO_CONTRACT_ID, todo_State.copy(participants = mutableListOf(PartyB)) )
                command(listOf(PartyB.owningKey), commandData = TodoContract.Commands.Uninvite())
                failsWith("Owner must be present in the output,")
            }
        }
    }
    @Test
    fun `Uninvite - Signing is required`() {
        ledgerServices.ledger {
            transaction {
                input(TodoContract.TODO_CONTRACT_ID, todo_State.copy(participants = mutableListOf(PartyA, PartyB)) )
                output(TodoContract.TODO_CONTRACT_ID, todo_State.copy(participants = mutableListOf(PartyA)) )
                command(listOf(PartyB.owningKey), commandData = TodoContract.Commands.Uninvite())
                failsWith("Signing is required")
            }
        }
    }
}