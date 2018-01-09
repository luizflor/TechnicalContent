package net.todo

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import net.todo.model.Todo
import net.todo.model.Todos
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(TodoApplication::class),
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoRest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate
    @Before
    fun restSetup() {
    val result = testRestTemplate
            .getForEntity("/api/setup", Todos::class.java)

        assertNotNull(result)
        val todos = ((result as ResponseEntity<*>).body as Todos).data
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(20,todos.size)
        assertEquals(todos[0].id,1)
    }

    @Test
    fun restGetById(){
        val result = testRestTemplate.getForEntity("/api/todos/1", Todo::class.java)

        assertNotNull(result)
        val todo = ((result as ResponseEntity).body as Todo)
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(todo.id,1)
    }

    @Test
    fun restCreateTodo(){
        var newTodo = Todo(0,"new todo", false)
        val result = testRestTemplate.postForEntity("/api/todos",newTodo,Todo::class.java)
        assertNotNull(result)
        val todo = ((result as ResponseEntity).body as Todo)
        assertEquals(HttpStatus.CREATED, result.statusCode)
        assertEquals(todo.id,21)
    }
    @Test
    fun restUpdateTodo(){
        var newTodo = Todo(0,"update todo", false)
        var request = HttpEntity<Todo>(newTodo)
        var result =testRestTemplate.exchange("/api/todos/1",HttpMethod.PUT,request,Todo::class.java)
        assertNotNull(result)
        var todo =(result as ResponseEntity).body as Todo
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals("update todo",todo.todo)
    }
    @Test
    fun restDeleteTodo(){
        var result =testRestTemplate.exchange("/api/todos/1",HttpMethod.DELETE,null,Todo::class.java)
        assertNotNull(result)
        var todo =(result as ResponseEntity).body as Todo
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(1,todo.id)
    }
    @Test
    fun restGetByIdNotFound(){
        val result = testRestTemplate.getForEntity("/api/todos/9999", Todo::class.java)

        assertNotNull(result)
        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }
}