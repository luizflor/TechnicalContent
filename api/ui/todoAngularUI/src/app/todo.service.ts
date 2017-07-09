import {Injectable} from '@angular/core';
import {Http, Response, RequestOptions, RequestMethod, Request, Headers, RequestOptionsArgs} from "@angular/http";
import 'rxjs/Rx';
import {Observable} from "rxjs/Observable";
import {Todo} from "./model/Todo";

@Injectable()
export class TodoService {

  private todos: Todo[];

  private url: string = "http://localhost:3000";

  constructor(private http: Http) {
  }

  addUpdate(todo:Todo): Observable<Todo>{
    let url = this.url + "/api/todos";

    let headers = new Headers({'Content-Type':'application/json'});
    let options = new RequestOptions({headers: headers});

    return this.http.post(url,todo,options)
               .map(resp => resp.json() as Todo[])
               .catch((error: Response) => this.handleError(error));
  }

  delete(todo:Todo): Observable<Todo>{
    let url = this.url + "/api/todos";

    let headers = new Headers({'Content-Type':'application/json'});
    let options = new RequestOptions({headers: headers, body:todo});

    return this.http.delete(url,options)
               .map(resp => resp.json() as Todo[])
               .catch((error: Response) => this.handleError(error));
  }

  get(id:string): Observable<Todo> {
    let url = this.url + "/api/todos/"+id;
    return this.http.get(url)
               .map(resp => resp.json() as Todo[])
               .catch((error: Response) => this.handleError(error));
  }

  getTodos(): Observable<Todo[]> {
    let url = this.url + "/api/todos";
    return this.http.get(url)
               .map(resp => resp.json() as Todo[])
               .catch((error: Response) => this.handleError(error));
  }

  setup(): Observable<Todo[]> {
    let url = this.url + "/api/setup";
    return this.http.get(url)
               .map(resp => resp.json() as Todo[])
               .catch((error: Response) => this.handleError(error));
  }

  private handleError(error: Response) {
    console.log(JSON.stringify(error));
    return Observable.throw(error);
  }
}
