package net.todo

import junit.framework.Assert.assertEquals
import net.todo.model.Todo
import net.todo.repository.TodoRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class TodoApplicationTests {


	@Test
	fun contextLoads() {
	}
	@Autowired
	lateinit var todoRepository: TodoRepository
	@Before
	fun setup(){
//		val mockRepository = mock(TodoRepository::class.java)
//		`when`(mockRepository.setup()).thenReturn(mutableListOf<Todo>())
		val todos = todoRepository.setup()
		assertEquals(20,todos.size)
	}

	@Test
	fun getAll(){
		val todos = todoRepository.getAll()
		assertEquals(20,todos.size)
	}

	@Test
	fun getTodoById(){
		val todo = todoRepository.getTodoById(1)
		assertEquals(1,todo?.id)
	}

	@Test
	fun createTodo(){
		val newTodo = Todo(0,"New TODO",false)
		val tempTodo = todoRepository.createTodo(newTodo)
		val todos = todoRepository.getAll()
		assertEquals(21,todos.size)
		assertEquals(21,tempTodo.id)
	}
	@Test
	fun updateTodo(){
		val updateTodo = Todo(0,"UPDATE TODO",false)
		val tempTodo = todoRepository.updateTodo(1, updateTodo)
		val todo = todoRepository.getTodoById(1)!!
		assertEquals(tempTodo.id,todo.id)
		assertEquals(tempTodo.todo,todo.todo)
		assertEquals(tempTodo.isDone,todo.isDone)
	}

	@Test
	fun deleteTodo(){
		val tempTodo = todoRepository.deleteTodo(1)
		assertEquals(1,tempTodo.id)
		val todo = todoRepository.getTodoById(1)
		assertEquals(null,todo)
		val todos = todoRepository.getAll()
		assertEquals(19,todos.size)
	}

	@Test(expected = IllegalArgumentException::class)
	fun deleteTodoExcecption(){
		val tempTodo = todoRepository.deleteTodo(Int.MAX_VALUE)
		val todo = todoRepository.getTodoById(1)
		assertEquals(null,todo)
		val todos = todoRepository.getAll()
		assertEquals(19,todos.size)
	}

	@Test
	fun getTodoByIdNotFound(){
		val todo = todoRepository.getTodoById(999)
		assertEquals(null,todo?.id)
	}

}