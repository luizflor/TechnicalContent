import { Component, OnInit } from '@angular/core';
import {User} from '../model/User';
import {TodoService} from '../todo.service';
import {Token} from '../model/Token';
import {AuthService} from '../auth/auth.service';

@Component({
  selector: 'td-loggin',
  templateUrl: './loggin.component.html',
  styleUrls: ['./loggin.component.css']
})
export class LogginComponent implements OnInit {

  user: User = new User('admin', 'password');
  constructor(private todoService: TodoService, private authService: AuthService) { }

  ngOnInit() {
    // this.user= new User('admin', 'password');
    console.log(this.user);
  }
  onLoggin () {
    this.todoService.loggin(this.user).subscribe(
      (data: Response) => {
        const token = data.headers.get('authorization');
        this.authService.setToken(new Token(token));
      },
      e => {
        //alert(e);
        console.log('[loginComponent]', e);
      }
    );
  }

  showLogin() {
    return window.location.origin === 'http://localhost:4200' ? false : true;
  }

}
