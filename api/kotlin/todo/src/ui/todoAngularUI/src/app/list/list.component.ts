import { Component, OnInit } from '@angular/core';
import {TodoService} from '../todo.service';
import {Todo} from '../model/Todo';
// import { StompService } from '@stomp/ng2-stompjs';
import {Subscription} from 'rxjs/Subscription';
import {Observable} from 'rxjs/Observable';

@Component({
  selector: 'td-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  private todos: Todo[];

  constructor(private todoService: TodoService) {  }
  // constructor(private todoService: TodoService, private stomp: StompService) {
  // }
  //response
  public response = (data) => {
    console.log(data);
  }

  ngOnInit() {
    this.getTodos();
    this.stompSetup();
  }
  // Stream of messages

  stompSetup() {
    /*
    //configuration
    this.stomp.configure({
      host: 'localhost:3030',
      debug: true,
      queue: {'init': false}
    });

    //start connection
    this.stomp.startConnect().then(() => {
      this.stomp.done('init');
      console.log('connected');

      //subscribe
      this.subscription = stomp.subscribe('/stomp', this.response);

      //send data
      this.stomp.send('destionation', {'data': 'data'});

      //unsubscribe
      this.subscription.unsubscribe();

      //disconnect
      this.stomp.disconnect().then(() => {
        console.log( 'Connection closed' );
      });

    });
    */
  }

  getTodos(){
    this.todoService.getTodos().subscribe(
      (data) => {
        if (data.length === 0){
          //this.setup();
          return;
        }
        this.todos = data;
      },
      e => {
        alert(e);
      }
    );
  }

  setup(){
    this.todoService.setup().subscribe(
      (data) => {
         this.todos = data;
      },
      e => {
        alert(e);
      }
    );
  }

}
