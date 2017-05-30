package todo.model;

/**
 * Created by luizsilva on 5/28/17.
 */

public class Todo {
    public Todo() {
    }

    public Todo(int id, String todo, boolean isDone) {
        this.id = id;
        this.todo = todo;
        this.isDone = isDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    private int id;
    private String todo;
    private boolean isDone;
}