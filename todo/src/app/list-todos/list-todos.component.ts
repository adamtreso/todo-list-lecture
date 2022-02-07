import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TodoDataService } from '../service/data/todo-data.service';
import { JwtAuthenticationService } from '../service/jwt-authentication.service';

export class Todo{
  constructor(
    public id:number,
    public username:string,
    public description:string,
    public targetDate:Date,
    public done:boolean){}
}

@Component({
  selector: 'app-list-todos',
  templateUrl: './list-todos.component.html',
  styleUrls: ['./list-todos.component.css']
})
export class ListTodosComponent implements OnInit {

  todos : Todo[]
  message : string
  errorMessage : string
  
  constructor(
    private authService:JwtAuthenticationService,
    private todoService:TodoDataService,
    private router:Router
  ) { }

  ngOnInit() {
    this.updateTodos()
  }

  deleteTodo(id:number){
    this.todoService.deleteTodo(this.authService.getAuthenticatedUser(), id).subscribe(
      response => {
        this.message = `Delete was successful`
        this.errorMessage = ""
        this.updateTodos()
      },
      error => {
        this.errorMessage = `Delete was unsuccessful`
        this.message = ""
      }
    );
  }

  updateTodo(id){
    this.router.navigate(['todos', id])
  }

  updateTodos(){
    this.todoService.retrieveAllTodos(this.authService.getAuthenticatedUser()).subscribe(
      response => {
        this.todos = response
      },
      error => {
        this.errorMessage = error.error.message;
        this.message = ""
      }
    )
  }

  addTodo(){
    this.router.navigate(['todos', -1])
  }

  undoneTodo(todo:Todo){
    todo.done = false;
    this.todoService.updateTodo(this.authService.getAuthenticatedUser(), todo.id, todo).subscribe(
    response => {
      this.updateTodos()
    },
    error => {
      this.errorMessage = `Delete was unsuccessful`
      this.message = ""
    })
  }

  doneTodo(todo:Todo){
    todo.done = true;
    this.todoService.updateTodo(this.authService.getAuthenticatedUser(), todo.id, todo).subscribe(
    response => {
      this.updateTodos()
    },
    error => {
      this.errorMessage = `Delete was unsuccessful`
      this.message = ""
    })
  }
}
