package todo.controllers;

/**
 * Created by luizsilva on 5/28/17.
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todo.model.Todo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController {
    private List<Todo> todoList = new ArrayList<Todo>();

    @RequestMapping(path = "/setup", method = RequestMethod.GET)
    public ResponseEntity<List<Todo>> Setup() {
        for (int i = 1; i <= 20; i++) {
            todoList.add(new Todo(i, "task." + i, true));
        }
        return new ResponseEntity<List<Todo>>(todoList, HttpStatus.OK);
    }

    @RequestMapping(path = "/todos", method = RequestMethod.GET)
    public ResponseEntity<List<Todo>> todos() {
        return new ResponseEntity<List<Todo>>(todoList, HttpStatus.OK);
    }

    @RequestMapping(path = "/todos/{id}", method = RequestMethod.GET)
    public ResponseEntity<Todo> getTodo(@PathVariable("id") long id) {
        for (Todo todo : todoList) {
            if (todo.getId() == id) {
                return new ResponseEntity<Todo>(todo, HttpStatus.OK);
            }
        }
        return new ResponseEntity(new CustomErrorType("id " + id + " not found."), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/todos", method = RequestMethod.POST)
    public ResponseEntity<?> createTodo(@RequestBody Todo newTodo) {

        for (Todo todo : todoList) {
            if (todo.getId() == newTodo.getId()) {
                return new ResponseEntity(new CustomErrorType("task " + newTodo.getTodo() + " already exist."), HttpStatus.CONFLICT);
            }
        }
        int id = todoList.size() == 0 ? 1 : todoList.get(todoList.size() - 1).getId() + 1;
        newTodo.setId(id);
        todoList.add(newTodo);
        return new ResponseEntity<Todo>(newTodo, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/todos/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTodo(@PathVariable("id") int id, @RequestBody Todo newTodo) {

        for (Todo todo : todoList) {
            if (todo.getId() == newTodo.getId()) {
                todo.setDone(newTodo.isDone());
                todo.setTodo(newTodo.getTodo());
                return new ResponseEntity<Todo>(todo, HttpStatus.OK);
            }
        }
        return new ResponseEntity(new CustomErrorType("Unable to upate. Todo with id " + id + " not found."), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/todos/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTodo(@PathVariable("id") int id) {
        Todo todo = null;
        for (Iterator<Todo> iterator = todoList.iterator(); iterator.hasNext(); ) {
            todo = iterator.next();
            if (todo.getId() == id) {
                iterator.remove();
                return new ResponseEntity<Todo>(todo, HttpStatus.OK);
            }
        }
        return new ResponseEntity(new CustomErrorType("Unable to delete. todo with id " + id + " not found."), HttpStatus.NOT_FOUND);
    }
}
