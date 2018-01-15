package net.todo.repository

import net.todo.model.Todo
import org.springframework.stereotype.Component

@Component
public class TodoRepository {
    constructor()

    private  var todoList: MutableList<Todo>? = null
    fun setup(): List<Todo>{
        todoList = mutableListOf<Todo>()
        (1..20).forEach { i -> todoList!!.add(Todo(i, "task." + i, true)) }
        return todoList as MutableList<Todo>
    }

    fun getAll(): List<Todo> {
        if(this.todoList ==null) {
            this.todoList = mutableListOf()
        }
        return this.todoList!!
    }

    fun getTodoById(id: Int): Todo? {
        return todoList!!.firstOrNull{it.id == id}
    }

    fun createTodo(newTodo: Todo): Todo {
        var tempTodo: Todo;
        if (todoList != null) {
            val filterTodos = todoList!!.filter { it.id == newTodo.id }
            if (filterTodos.isNotEmpty()) throw IllegalArgumentException("todo already exist")
            val id = if (todoList!!.size == 0) 1 else todoList!![todoList!!.size - 1].id + 1
            tempTodo = newTodo.copy(id = id)
            todoList!!.add(tempTodo)
        } else {
            tempTodo = newTodo.copy(id = 1)
            todoList = mutableListOf()
            todoList!!.add(tempTodo)
        }
        return tempTodo
    }

    fun updateTodo(id:Int, newTodo: Todo): Todo {
        val todo = todoList!!.firstOrNull { it.id == id }
                ?: throw IllegalArgumentException("todo not found")
        todo.isDone = newTodo.isDone
        todo.todo = newTodo.todo

        return todo!!
    }

    fun deleteTodo(id:Int): Todo{
        val todo = todoList!!.firstOrNull { it.id == id }
                ?: throw IllegalArgumentException("todo not found")
        todoList!!.removeIf { it.id == id }
        return todo
    }
}