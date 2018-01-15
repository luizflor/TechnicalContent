import { Component, OnInit } from '@angular/core';
import {Token} from '../model/Token';
import {TodoService} from '../todo.service';
import * as jwtDecode from 'jwt-decode';
import {AuthService} from '../auth/auth.service';

@Component({
  selector: 'td-token',
  templateUrl: './token.component.html',
  styleUrls: ['./token.component.css']
})
export class TokenComponent implements OnInit {

  private token: Token= new Token('');
  constructor(private authService: AuthService) { }

  ngOnInit() {
  }
  onSetToken() {
    //  const token = (jwtDecode(this.token.token));
    // const userName = (jwtDecode(this.token.token).sub;
    // const expirationSeconds = (new Date(token.exp * 1000).getTime() - new Date().getTime()) / 1000;
    // console.log(expirationSeconds, userName);

    this.authService.setToken(this.token);
  }

}
