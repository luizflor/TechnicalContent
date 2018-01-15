import { Component, OnInit } from '@angular/core';
import {TodoService} from "../todo.service";
import {Todo} from "../model/Todo";

@Component({
  selector: 'td-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  private todos: Todo[];
  constructor(private todoService: TodoService) { }

  ngOnInit() {
    this.getTodos();
  }

  getTodos(){
    this.todoService.getTodos().subscribe(
      (data)=> {
        if(data.length == 0){
          this.setup()
        }
        this.todos = data;
      },
      e=>{
        alert(e);
      }
    );
  }

  setup(){
    this.todoService.setup().subscribe(
      (data)=> {
         this.todos = data;
      },
      e=>{
        alert(e);
      }
    );
  }

}
