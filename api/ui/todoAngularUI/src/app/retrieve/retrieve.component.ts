import { Component, OnInit } from '@angular/core';
import {TodoService} from "../todo.service";
import {Todo} from "../model/Todo";

@Component({
  selector: 'td-retrieve',
  templateUrl: './retrieve.component.html',
  styleUrls: ['./retrieve.component.css']
})
export class RetrieveComponent implements OnInit {

  private todo:Todo = new Todo();
  constructor(private todoService: TodoService) { }

  ngOnInit() {
  }

  onRetrieve() {
    this.todoService.get(this.todo.id.toString()).subscribe(
      (data)=>{
        this.todo =data;
      },
      e=>{
        alert(e);
      }
    );
  }

}
