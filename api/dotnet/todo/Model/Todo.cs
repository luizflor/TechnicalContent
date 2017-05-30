using System;
namespace todo.Model
{
    public class Todo
    {
        public Todo(int id, bool isDone, string todo)
        {
            this.id = id;
            this.isDone = isDone;
            this.todo = todo;
        }
		public int id
		{
			get;
			set;
		}
        public bool isDone
        {
            get;
            set;
        }
        public string todo
        {
            get;
            set;
        }
	}
}
