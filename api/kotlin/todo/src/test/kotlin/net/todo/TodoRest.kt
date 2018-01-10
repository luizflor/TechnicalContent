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
import org.springframework.http.*
import org.springframework.test.context.junit4.SpringRunner

data class User(val username:String, val password:String)

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(TodoApplication::class),
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoRest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    val user = User("admin", "password")

    fun getHeaders(): HttpHeaders{
        val login = testRestTemplate.postForEntity<Any>("/login",user,null)

        val token = (login as ResponseEntity).headers["Authorization"]

        val headers = HttpHeaders()
        if(token!=null) {
            headers["Authorization"] = token
        }
        return headers
    }

    @Before
    fun restSetup() {
        var headers = getHeaders()
        if (headers.size > 0) return
        val resultSignup = testRestTemplate.postForEntity<Any>("/users/sign-up", user, null)

        headers = getHeaders()
        val entity = HttpEntity("", headers)
        val url = "/api/setup"

        val result = testRestTemplate.exchange(url, HttpMethod.GET, entity, Todos::class.java)

        assertNotNull(result)
        val todos = ((result as ResponseEntity<*>).body as Todos).data
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(20, todos.size)
        assertEquals(todos[0].id, 1)
    }

    @Test
    fun restGetById() {
        val headers = getHeaders()
        val entity = HttpEntity("", headers)
        val url = "/api/todos/1"
        val result = testRestTemplate.exchange(url, HttpMethod.GET, entity, Todo::class.java)

        assertNotNull(result)
        val todo = ((result as ResponseEntity).body as Todo)
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(todo.id, 1)
    }

    @Test
    fun restCreateTodo(){
        val newTodo = Todo(0,"new todo", false)
        val headers = getHeaders()
        val entity = HttpEntity(newTodo, headers)
        val url = "/api/todos"
        val result = testRestTemplate.exchange(url, HttpMethod.POST, entity, Todo::class.java)

        assertNotNull(result)
        val todo = ((result as ResponseEntity).body as Todo)
        assertEquals(HttpStatus.CREATED, result.statusCode)
        assertEquals(todo.id,21)
    }
    @Test
    fun restUpdateTodo(){
        val newTodo = Todo(0,"update todo", false)
        val headers = getHeaders()
        val entity = HttpEntity(newTodo, headers)
        val url = "/api/todos/1"

        val result =testRestTemplate.exchange(url,HttpMethod.PUT,entity,Todo::class.java)
        assertNotNull(result)
        val todo =(result as ResponseEntity).body as Todo
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals("update todo",todo.todo)
    }
    @Test
    fun restDeleteTodo(){
        val headers = getHeaders()
        val entity = HttpEntity("", headers)
        val url = "/api/todos/1"
        val result =testRestTemplate.exchange(url,HttpMethod.DELETE,entity,Todo::class.java)
        assertNotNull(result)
        val todo =(result as ResponseEntity).body as Todo
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(1,todo.id)
    }
    @Test
    fun restGetByIdNotFound(){
        val headers = getHeaders()
        val entity = HttpEntity("", headers)
        val url = "/api/todos/999"
        val result = testRestTemplate.exchange(url, HttpMethod.GET, entity, Todo::class.java)

        assertNotNull(result)
        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }

}
