import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { ListComponent } from './list/list.component';
import { AddComponent } from './add/add.component';
import {routing} from './todo.routes';
import {HttpModule} from '@angular/http';
import { UpdateComponent } from './update/update.component';
import { DeleteComponent } from './delete/delete.component';
import { RetrieveComponent } from './retrieve/retrieve.component';
import {TodoService} from './todo.service';
import { SignupComponent } from './signup/signup.component';
import { LogginComponent } from './login/loggin.component';
import { TokenComponent } from './token/token.component';
import {AuthService} from './auth/auth.service';
import {AuthGuard} from './auth/AuthGuard';
// import {StompConfig, StompService} from '@stomp/ng2-stompjs';

@NgModule({
  declarations: [
    AppComponent,
    ListComponent,
    AddComponent,
    UpdateComponent,
    DeleteComponent,
    RetrieveComponent,
    SignupComponent,
    LogginComponent,
    TokenComponent
  ],
  imports: [
    BrowserModule, HttpModule, FormsModule, routing
  ],
  providers: [TodoService, AuthService, AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
