package net.todo.model

import org.springframework.stereotype.Component

@Component
data class Todo(var id:Int,
                   var todo:String,
                   var isDone:Boolean)  {
    constructor() : this(0,"",false)

    override fun toString(): String {
        return "id: $id todo:$todo isDone:$isDone"
    }
}
data class Todos(var data: List<Todo>){
    constructor():this(mutableListOf<Todo>())
}