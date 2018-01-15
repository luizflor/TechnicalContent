package net.todo.controllers

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import net.todo.model.Todo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import net.todo.model.CustomErrorType
import net.todo.model.Todos
import net.todo.repository.TodoRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.*

@Api(basePath = "/api", value = "Todos", description = "Operations with Todos", produces = "application/json")
@RestController
@RequestMapping("/api")
class TodoController(private val template: SimpMessagingTemplate) {

    companion object {
        private val logger = LoggerFactory.getLogger(RestController::class.java)
    }
    @Autowired
    lateinit var todoRepository: TodoRepository

    var todoList = mutableListOf<Todo>()
    //http://localhost:3030/swagger-ui
    @ApiOperation(value = "Setup array for todos", notes = "some notes")
    @GetMapping(path = ["/setup"])
    fun setup(): ResponseEntity<Todos> {
        logger.info("[setup] /setup")
        val data = Todos(todoRepository.setup())
        return ResponseEntity(data, HttpStatus.OK)
    }

    @GetMapping(path = ["/todos"])
    fun todos(): ResponseEntity<List<Todo>> {
        logger.info("[todos] /todos")
        val todos = todoRepository.getAll()
        return ResponseEntity(todos, HttpStatus.OK)
    }

    @GetMapping(path = ["/todos/{id}"])
    fun getTodo(@PathVariable("id") id: Int): ResponseEntity<*> {
        logger.info("[getTodo] /todo/$id")
        val todo = todoRepository.getTodoById(id)
        if (todo == null)
            return ResponseEntity(CustomErrorType("id $id not found."), HttpStatus.NOT_FOUND)
        else
            return ResponseEntity(todo, HttpStatus.OK)
    }

    @PostMapping(path = ["/todos"])
    fun createTodo(@RequestBody newTodo: Todo): ResponseEntity<*> {
        logger.info("[createTodo] /todos")
        try {
            val tempTodo = todoRepository.createTodo(newTodo)
            val todo = tempTodo
            if(template.defaultDestination!=null) {
                template.convertAndSend(todo)
            }
            return ResponseEntity(tempTodo, HttpStatus.CREATED)
        } catch (e: Exception) {
            e.printStackTrace()

            logger.error(e.toString())
            return ResponseEntity(CustomErrorType("task " + newTodo.id + " already exist."), HttpStatus.CONFLICT)
        }
    }

    @PutMapping(path = ["/todos/{id}"])
    fun updateTodo(@PathVariable("id") id: Int, @RequestBody newTodo: Todo): ResponseEntity<*> {
        logger.info("[updateTodo] /todos/$id")
        try {
            val tempTodo = todoRepository.updateTodo(id, newTodo)
            return ResponseEntity(tempTodo, HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity(CustomErrorType("task " + id + " not found."), HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping(path = ["/todos/{id}"])
    fun deleteTodo(@PathVariable("id") id: Int): ResponseEntity<*> {
        logger.info("[deleteTodo] /todos/$id")
        try {
            val tempTodo = todoRepository.deleteTodo(id)
            return ResponseEntity(tempTodo, HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity(CustomErrorType("task " + id + " not found."), HttpStatus.NOT_FOUND)
        }
    }
}