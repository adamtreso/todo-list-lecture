import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Message } from 'primeng/api';
import { TodoDto, TodoService } from '../service/todo.service';

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.css']
})
export class TodoListComponent implements OnInit {

  public todos:TodoDto[] = [];
  public messages:Message[] = [];

  constructor(
    private todoService : TodoService,
    private router : Router,
  ) { }

  ngOnInit(): void {
    this.updateTodos();
  }

  updateTodos(){
    this.todoService.getAllTodos().subscribe({
      next : (todoList) => {
        this.todos = todoList;
      },
      error : (error) => {
        console.log(error);
      }
    })
  }

  handleIsDoneChange(todo:TodoDto){
    this.todoService.updateTodo(todo).subscribe({
      next : (updatedTodo) => {
        this.insertUpdatedTodo(updatedTodo);
      },
      error : (error) => {
        console.log(error);
      }
    })
  }

  private insertUpdatedTodo(updatedTodo : TodoDto) : void{
    if (updatedTodo !== null)
    {
      for (let i = 0; i < this.todos.length; i++){
        if (this.todos[i].id == updatedTodo.id){
          this.todos[i] = updatedTodo;
          break;
        }
      }
    }
  }

  handleUpdateCommand(todo:TodoDto){
    this.router.navigate(['/todos', todo.id]);
  }

  handleDeleteCommand(todo:TodoDto){
    this.todoService.deleteTodo(todo.id).subscribe({
      next : () => {
        this.removeTodo(todo);
        this.messages = [];
        this.messages.push({severity:'success', summary:'Deleted', detail:'The task was deleted from your todos!'});
      },
      error : (error) => {
        console.log(error);
        this.messages.push({severity:'error', summary:'Error', detail:'Someting went wrong, the task was not deleted from your todos!'});
      }
    })
  }

  private removeTodo(deleteTodo:TodoDto){
    if (deleteTodo !== null){
      for (let i = 0; i < this.todos.length; i++){
        if (this.todos[i].id == deleteTodo.id){
          this.todos.splice(i, 1);
        }
      }
    }
  }

  newTodo(){
    this.router.navigate(['/todos', -1])
  }
}
