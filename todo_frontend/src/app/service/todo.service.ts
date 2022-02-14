import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL } from '../app.constants';
import { AuthenticationService } from './authentication.service';

export class TodoDto{
  constructor(
    public id:number,
    public username:string,
    public description:string,
    public targetDate:Date,
    public done:boolean
  ){}
}

@Injectable({
  providedIn: 'root'
})
export class TodoService {

  constructor(private http:HttpClient ,private authService : AuthenticationService) { }

  getAllTodos(){
    return this.http.get<TodoDto[]>(`${API_URL}/users/${this.authService.getLoggedInUser()}/todos`)
  }

  deleteTodo(id:number){
    return this.http.delete(`${API_URL}/users/${this.authService.getLoggedInUser()}/todos/${id}`)
  }

  getTodo(id:number){
    return this.http.get<TodoDto>(`${API_URL}/users/${this.authService.getLoggedInUser()}/todos/${id}`)
  }

  updateTodo(todo:TodoDto){
    return this.http.put<TodoDto>(`${API_URL}/users/${this.authService.getLoggedInUser()}/todos/${todo.id}`,todo)
  }

  addTodo(todo:TodoDto){
    return this.http.post(`${API_URL}/users/${this.authService.getLoggedInUser()}/todos`, todo)
  }
}
