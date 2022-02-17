// import { Component, OnInit } from '@angular/core';
// import { ActivatedRoute, Router } from '@angular/router';
// import { AuthenticationService } from '../service/authentication.service';
// import { TodoDto, TodoService } from '../service/todo.service';

// @Component({
//   selector: 'app-todo',
//   templateUrl: './todo.component.html',
//   styleUrls: ['./todo.component.css']
// })
// export class TodoComponent implements OnInit {

//   todo:TodoDto;

//   constructor(
//     private router : Router,
//     private route : ActivatedRoute,
//     private todoService : TodoService,
//     private authService : AuthenticationService,
//   ) { }

//   ngOnInit(): void {
//     let id = this.route.snapshot.params["id"];
//     console.log(id)
//     this.todo = new TodoDto(id, this.authService.getLoggedInUser(), "", new Date(), false);
//     if (this.todo.id != -1){
//       this.todoService.getTodo(this.todo.id).subscribe({
//         next : (responseTodo) => {
//           this.todo.description = responseTodo.description;
//           this.todo.targetDate = responseTodo.targetDate;
//           this.todo.done = responseTodo.done;
//         },
//         error : (error) => {
//           console.log(error);
//         }
//       });
//     }
    
//   }

//   saveTodo(){
//     if (this.todo.id == -1){
//       this.todoService.addTodo(this.todo).subscribe({
//         next : () => {
//           this.router.navigate(['/todos']);
//         },
//         error : (error) => {
//           console.log(error);
//         }
//       })
//     }else{
//       this.todoService.updateTodo(this.todo).subscribe({
//         next : () => {
//           this.router.navigate(['/todos']);
//         },
//         error : (error) => {
//           console.log(error);
//         }
//       })
//     }
//   }
// }
