import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Todo } from '../list-todos/list-todos.component';
import { TodoDataService } from '../service/data/todo-data.service';
import { JwtAuthenticationService } from '../service/jwt-authentication.service';

@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.css']
})
export class TodoComponent implements OnInit {

  id:number
  errorMessage:string
  todo:Todo

  constructor(
    private authService:JwtAuthenticationService,
    private route:ActivatedRoute,
    private router:Router,
    private todoService:TodoDataService
  ) { }

  ngOnInit() {
    this.id = this.route.snapshot.params["id"];
    this.todo = new Todo(this.id, this.authService.getAuthenticatedUser(), '', new Date(), false)
    if (this.id == -1){
      //if new todo
    }else{
      //if updateing
      this.todoService.retrieveTodo(this.authService.getAuthenticatedUser(), this.id).subscribe(
        data => {
          this.todo = data
        },
        error => {
          this.errorMessage = "Something went wrong!"
        }
      )
    }
  }

  save(){
    if (this.id == -1){
      // if new todo
      this.todoService.addTodo(this.authService.getAuthenticatedUser(), this.todo).subscribe(
        createdUri => {
          this.router.navigate(["todos"])
        },
        error => {
          this.errorMessage = error.error.message
        }
      )
    }else{
      //if updating
      this.todoService.updateTodo(this.authService.getAuthenticatedUser(), this.id, this.todo).subscribe(
        data => {
          this.router.navigate(["todos"])
        },
        error => {
          this.errorMessage = "Someting went wrong"
        }
      )
    }
    }
}
