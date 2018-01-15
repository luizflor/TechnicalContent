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
		if(todoRepository.getAll().isEmpty()) return

		val todos = todoRepository.getAll()
		//assertEquals(20,todos.size)
		assert(todos.isNotEmpty())
	}

	@Test
	fun getTodoById(){
		if(todoRepository.getAll().isNotEmpty()) {
			val todo = todoRepository.getTodoById(1)
			assertEquals(1, todo?.id)
		}
	}

	@Test
	fun createTodo() {
		val newTodo = Todo(0, "New TODO", false)
		val todosBefore = todoRepository.getAll().size
		val tempTodo = todoRepository.createTodo(newTodo)
		val todosAfter = todoRepository.getAll().size
		assertEquals(todosBefore + 1, todosAfter)
		assertEquals(todosBefore + 1, tempTodo.id)
	}
	@Test
	fun updateTodo(){
		if(todoRepository.getAll().isEmpty()) return

		val updateTodo = Todo(0,"UPDATE TODO",false)
		val tempTodo = todoRepository.updateTodo(1, updateTodo)
		val todo = todoRepository.getTodoById(1)!!
		assertEquals(tempTodo.id,todo.id)
		assertEquals(tempTodo.todo,todo.todo)
		assertEquals(tempTodo.isDone,todo.isDone)
	}

	@Test
	fun deleteTodo(){
		if(todoRepository.getAll().isNotEmpty()) {
			val todos = todoRepository.getAll()
			val id = todos[0].id
			val sizeBefore = todos.size
			val tempTodo = todoRepository.deleteTodo(id)
			assertEquals(id, tempTodo.id)
			val todo = todoRepository.getTodoById(id)
			assertEquals(null, todo)
			val sizeAfter = todoRepository.getAll().size
			assertEquals( sizeBefore-1, sizeAfter)
		}
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
