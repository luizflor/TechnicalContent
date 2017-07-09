import { Component, OnInit } from '@angular/core';
import {TodoService} from "../todo.service";
import {Todo} from "../model/Todo";

@Component({
  selector: 'td-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css']
})
export class UpdateComponent implements OnInit {

  private todo:Todo = new Todo();
  constructor(private todoService: TodoService) { }

  ngOnInit() {
  }

  onUpdate() {
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
