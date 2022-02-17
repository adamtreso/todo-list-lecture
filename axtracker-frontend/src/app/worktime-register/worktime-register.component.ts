import { Component, OnInit } from '@angular/core';
import { Message } from 'primeng/api';
import { WorkLogEntryDto, WorkLogEntryService } from '../service/work-log-entry.service';

@Component({
  selector: 'app-worktime-register',
  templateUrl: './worktime-register.component.html',
  styleUrls: ['./worktime-register.component.css']
})
export class WorktimeRegisterComponent implements OnInit {

  messages : Message[] = [];
  workLogEntrys : WorkLogEntryDto[] = [];

  constructor(
    private workLogEntrySerivce : WorkLogEntryService,
  ) { }

  ngOnInit(): void {
    this.updateWorkLogEntrys();
  }

  updateWorkLogEntrys(){
    this.workLogEntrySerivce.getAll().subscribe({
      next : (workLogEntryList) => {
        this.workLogEntrys = workLogEntryList;
      },
      error : (error) => {
        console.log(error);
      }
    })
  }

  handleUpdateCommand(workLogEntry:WorkLogEntryDto){
    //this.router.navigate(['/workLogEntry', todo.id]);
  }

  handleDeleteCommand(workLogEntry:WorkLogEntryDto){
    // this.todoService.deleteTodo(todo.id).subscribe({
    //   next : () => {
    //     this.removeTodo(todo);
    //     this.messages = [];
    //     this.messages.push({severity:'success', summary:'Deleted', detail:'The task was deleted from your todos!'});
    //   },
    //   error : (error) => {
    //     console.log(error);
    //     this.messages.push({severity:'error', summary:'Error', detail:'Someting went wrong, the task was not deleted from your todos!'});
    //   }
    // })
  }

  // private removeTodo(deleteTodo:TodoDto){
  //   if (deleteTodo !== null){
  //     for (let i = 0; i < this.todos.length; i++){
  //       if (this.todos[i].id == deleteTodo.id){
  //         this.todos.splice(i, 1);
  //       }
  //     }
  //   }
  // }

  newTodo(){
    //this.router.navigate(['/workLogEntry', -1])
  }
}
