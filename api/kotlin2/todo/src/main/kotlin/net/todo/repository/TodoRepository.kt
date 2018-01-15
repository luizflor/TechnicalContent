package net.todo.repository

import net.todo.model.Todo
import org.springframework.stereotype.Component

@Component
public class TodoRepository {
    constructor()

    private lateinit var todoList: MutableList<Todo>
    fun setup(): List<Todo>{
        todoList = mutableListOf<Todo>()
        (1..20).forEach { i -> todoList.add(Todo(i, "task." + i, true)) }
        return todoList
    }

    fun getAll(): List<Todo>{
        return todoList
    }

    fun getTodoById(id: Int): Todo? {
        return todoList.firstOrNull{it.id == id}
    }

    fun createTodo(newTodo: Todo): Todo{
        val filterTodos = todoList.filter { it.id == newTodo.id }
        if (filterTodos.isNotEmpty()) throw IllegalArgumentException("todo already exist")
        val id = if (todoList.size == 0) 1 else todoList[todoList.size - 1].id + 1
        val tempTodo = newTodo.copy(id = id)
        todoList.add(tempTodo)
        return tempTodo
    }

    fun updateTodo(id:Int, newTodo: Todo): Todo {
        val todo = todoList.firstOrNull { it.id == id }
                ?: throw IllegalArgumentException("todo not found")
        todo.isDone = newTodo.isDone
        todo.todo = newTodo.todo

        return todo!!
    }

    fun deleteTodo(id:Int): Todo{
        val todo = todoList.firstOrNull { it.id == id }
                ?: throw IllegalArgumentException("todo not found")
        todoList.removeIf { it.id == id }
        return todo
    }
}