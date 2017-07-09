import { Component, OnInit } from '@angular/core';
import {Todo} from "../model/Todo";
import {TodoService} from "../todo.service";

@Component({
  selector: 'td-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {

  private todo:Todo = new Todo();
  constructor(private todoService: TodoService) { }

  ngOnInit() {
  }

  onAdd() {
    this.todoService.addUpdate(this.todo).subscribe(
      (data)=>{
        this.todo =data;
      },
      e=>{
        alert(e);
      }
    );
  }
}
