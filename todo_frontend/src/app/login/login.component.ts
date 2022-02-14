import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Message } from 'primeng/api';
import { AuthenticationService } from '../service/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = "";
  password = "";
  messages:Message[] = [];

  constructor(
    private router : Router,
    private authenticationService : AuthenticationService
  ) { }

  ngOnInit() {
  }

  hanleLogin(){
    this.authenticationService.login(this.username, this.password).subscribe({
      next : (response) => {
        this.router.navigate(['/welcome'])
        this.messages = [];
      },
      error : (error) => {
        console.log(error)
        this.messages = []
        this.messages.push({severity:'warn', summary:'Bad credentials', detail:'Invalid username or password!'})
      }
    })
  }
}
