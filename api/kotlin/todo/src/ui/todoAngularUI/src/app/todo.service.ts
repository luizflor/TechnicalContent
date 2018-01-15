import {Injectable} from '@angular/core';
import {Http, Response, RequestOptions, RequestMethod, Request, Headers, RequestOptionsArgs} from '@angular/http';
import 'rxjs/Rx';
import {Observable} from 'rxjs/Observable';
import {Todo} from './model/Todo';
import {User} from './model/User';
import {Token} from './model/Token';
import * as jwtDecode from 'jwt-decode';
import {AuthService} from './auth/auth.service';

@Injectable()
export class TodoService {

  private todos: Todo[];

  private url = 'http://localhost:3030';

  constructor(private http: Http, private authService: AuthService) {
  }
  // private token: string;
  //
  // setToken(token: Token) {
  //
  //   //  const token = (jwtDecode(this.token.token));
  //   // const userName = (jwtDecode(this.token.token).sub;
  //   // const expirationSeconds = (new Date(token.exp * 1000).getTime() - new Date().getTime()) / 1000;
  //   // console.log(expirationSeconds, userName);
  //
  //   this.token = token.token;
  //   const tokenParse = (jwtDecode(token.token));
  //   const userName = tokenParse['sub'];
  //   const expirationSeconds = (new Date(tokenParse['exp'] * 1000).getTime() - new Date().getTime()) / 1000;
  //   console.log(expirationSeconds, userName);
  //
  //
  // }

  loggin(user: User): Observable<any> {
    const url = this.url + '/login';

    const headers = this.getHeaders();
    const body = JSON.stringify(user);
    const options = new RequestOptions({
      headers: headers,
      method: RequestMethod.Post,
      url: url,
      body: body
    });
    const req = new Request(options);

    return this.http.request(req);
    // return this.http.get(url, {  headers: headers })
    //   .map((response: Response) => {
    //     console.log('1',response);
    //     if (response.status === 202) {
    //       throw response;
    //     }
    //     return response;
    //   })
    //   .map((response: Response) => {
    //     console.log('2',response);
    //     return response.text() ? response.json() : '2';
    //   })
    //   .catch(res => {
    //     //return res.status === 202?
    //      return this.http.get(url, {  headers: headers })
    //       //Observable.throw(res);
    //   });
      // .retryWhen(obs => {
      //   console.log('3',obs);
      //   //return Observable.of('a');
      //   return obs; // always just resubscribe without any further logic
      // });
    //return Observable.of('');


    // return this.http.request(req)
    //   .subscribe(data=>{
    //     console.log('[data]',data);
    //     return Observable.of(data)
    //   })
      // .map(resp => {
      // console.log('[login]', resp);
      //  })
      // .catch(res => {
      //   return this.http.request(req)
      //     .map(res=>console.log('[]',res));
        // return res.status === 202?
        //   this.http.get(url):
        //   Observable.throw(res);
      //});
      // .flatMap((response: Response) => {
      //   if (response.status === 200) {
      //     return response //Observable.of('done');
      //   } else if (response.status === 202) {
      //     return this.http.request(req);
      //   }
      // })
  }

  addUpdate(todo: Todo): Observable<Todo> {
    const url = todo.id
      ? this.url + '/api/todos/' + todo.id
      : this.url + '/api/todos';

    const headers = this.getHeaders();
    const body = JSON.stringify(todo);
    const options = new RequestOptions({
      headers: headers,
      method: todo.id ? RequestMethod.Put : RequestMethod.Post,
      url: url,
      body: body
    });
    const req = new Request(options);
    return this.http.request(req)
               .map(resp => resp.json() as Todo[])
               .catch((error: Response) => this.handleError(error));
  }

  delete(todo: Todo): Observable<Todo> {
    const url = this.url + '/api/todos/' + todo.id;

    const headers = this.getHeaders();
    //const options = new RequestOptions({headers: headers, body: todo});
    const options = new RequestOptions({
      headers: headers,
      method: RequestMethod.Delete,
      url: url
    });
    const req = new Request(options);
    return this.http.request(req)
    //return this.http.delete(url, options)
               .map(resp => resp.json() as Todo[])
               .catch((error: Response) => this.handleError(error));
  }

  get(id: string): Observable<Todo> {
    const headers = this.getHeaders();
    const url = this.url + '/api/todos/' + id;
    const options = new RequestOptions({
      headers: headers,
      method: RequestMethod.Get,
      url: url
    });
    const req = new Request(options);
    return this.http.request(req)
    //return this.http.get(url)
               .map(resp => resp.json() as Todo[])
               .catch((error: Response) => this.handleError(error));
  }

  getTodos(): Observable<Todo[]> {
    const url = this.url + '/api/todos';
    const headers = this.getHeaders();
    const options = new RequestOptions({
      headers: headers,
      method: RequestMethod.Get,
      url: url
    });
    const req = new Request(options);
    return this.http.request(req)
    //return this.http.get(url)
               .map(resp => resp.json() as Todo[])
               .catch((error: Response) => this.handleError(error));
  }

  setup(): Observable<Todo[]> {
    const url = this.url + '/api/setup';
    const headers = this.getHeaders();
    const options = new RequestOptions({
      headers: headers,
      method: RequestMethod.Get,
      url: url
    });
    const req = new Request(options);
    return this.http.request(req)
   // return this.http.get(url)
               .map(resp => resp.json() as Todo[])
               .catch((error: Response) => this.handleError(error));
  }

  private handleError(error: Response) {
    console.log('[Error]', error);
    console.log(JSON.stringify(error));
    return Observable.throw(error);
  }

  getRequestOptions(url, method, body) {
    const headers = this.getHeaders();
    const options = new RequestOptions({
      headers: headers,
      method: RequestMethod.Post,
      url: url,
      body: body
    });
    return options;
  }
  getHeaders() {
    //const headers = new Headers({'Content-Type': 'application/json', 'Authorization' : this.token, 'Access-Control-Allow-Origin' : '*' });
    const headers = new Headers({'Content-Type': 'application/json', 'Authorization' : this.authService.token});
    return headers;
  }
}
