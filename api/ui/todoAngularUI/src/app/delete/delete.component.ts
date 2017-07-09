import { Component, OnInit } from '@angular/core';
import {TodoService} from "../todo.service";
import {Todo} from "../model/Todo";

@Component({
  selector: 'td-delete',
  templateUrl: './delete.component.html',
  styleUrls: ['./delete.component.css']
})
export class DeleteComponent implements OnInit {

  private todo:Todo = new Todo();
  constructor(private todoService: TodoService) { }

  ngOnInit() {
  }

  onDelete() {
    this.todoService.delete(this.todo).subscribe(
      (data)=>{
        this.todo =data;
      },
      e=>{
        alert(e);
      }
    );
  }

}
