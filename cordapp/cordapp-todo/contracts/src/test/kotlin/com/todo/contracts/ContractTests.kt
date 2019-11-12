package com.todo.contracts

import com.todo.states.TodoStatus
import net.corda.testing.node.MockServices
import net.corda.testing.node.ledger
import org.junit.Test
import kotlin.test.todo

class ContractTests: TodoContractTestBase() {

    @Test
    fun `Create - ok`() {
        ledgerServices.ledger {
            transaction {
                output(TodoContract.TODO_CONTRACT_ID, todo_State)
                command(signer = me.owningKey, commandData = TodoContract.Commands.Create())
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
                command(signer = me.owningKey, commandData = TodoContract.Commands.Cancel())
                verifies()
            }
        }
    }
    @Test
    fun `Complete - ok`() {
        ledgerServices.ledger {
            transaction {
                input(TodoContract.TODO_CONTRACT_ID, todo_State )
                command(signer = me.owningKey, commandData = TodoContract.Commands.Complete())
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
                command(signer = me.owningKey, commandData = TodoContract.Commands.Create())
                failsWith(" Todo transaction should have no input.")
            }
        }
    }

    @Test
    fun `Cancel - should not have an output`() {
        ledgerServices.ledger {
            transaction {
                input(TodoContract.TODO_CONTRACT_ID, todo_State)
                command(signer = me.owningKey, commandData = TodoContract.Commands.Cancel())
                failsWith("Todo transaction should have  output.")
            }
        }
    }

    @Test
    fun `Completed - should not have an output`() {
        ledgerServices.ledger {
            transaction {
                input(TodoContract.TODO_CONTRACT_ID, todo_State)
                output(TodoContract.TODO_CONTRACT_ID, todo_State)
                command(signer = me.owningKey, commandData = TodoContract.Commands.Complete())
                failsWith("Todo transaction should have no output.")
            }
        }
    }
}