using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using todo.Model;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace todo.Controllers
{
    [Route("api/[controller]")]
    public class TodosController : Controller
    {
        static List<Todo> todoList = new List<Todo>();

        [HttpGet]
        [Route("setup")]
        public IActionResult Setup()
        {
            todoList.Clear();
            Random random = new Random();
            for (int i = 1; i <=20; i++)
            {
                var rnd = random.Next(2);
                todoList.Add(new Todo(id: i, isDone: rnd == 0 ? false : true, todo: "task." + 1));
            }
            return Ok(todoList);
        }

        // GET: api/todos
        [HttpGet]
        [Route("")]
        public IActionResult GetAll()
        {
            return Ok(todoList);
        }

        // GET api/todos/5
        //[HttpGet("{id}")]
        [HttpGet]
        [Route("{id:int}")]
        public IActionResult Get(int id)
        {
            var todo = todoList.FirstOrDefault(x => x.id == id);
            if (todo == null)
            {
                return NotFound(id);
            }
            return Ok(todo);
        }

        // POST api/todos
        [HttpPost]
        [Route("")]
        public IActionResult Post([FromBody]Todo newTodo)
        {
            newTodo.id = todoList.Last().id + 1;
            todoList.Add(newTodo);
            return Ok(newTodo);
        }

        // PUT api/todos/5
        //[HttpPut("{id}")]
        [HttpPut]
        [Route("{id:int}")]
        public IActionResult Put(int id, [FromBody]Todo newTodo)
        {
            var todo = todoList.FirstOrDefault(x => x.id == id);
            if(todo ==null){
                return NotFound(id);
            }
            todo.isDone = newTodo.isDone;
            todo.todo = newTodo.todo;
            return Ok(todo);
        }

        // DELETE api/todos/5
        //[HttpDelete("{id}")]
        [HttpDelete]
        [Route("{id:int}")]
        public IActionResult Delete(int id)
        {
            var todo = todoList.FirstOrDefault(x => x.id == id);
            if (todo == null)
            {
                return NotFound(id);
            }
            todoList.Remove(todo);
            return Ok(todo);
        }
    }
}
