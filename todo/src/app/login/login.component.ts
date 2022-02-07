import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtAuthenticationService } from '../service/jwt-authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = ""
  password = ""
  errorMessage = "Invalid username or password!"
  invalidLogin = false

  constructor(private router : Router, private authenticationService : JwtAuthenticationService) { }

  ngOnInit() {
  }

  hanleLogin(){
    this.authenticationService.authenticate(this.username, this.password).subscribe(
      response => {
        this.router.navigate(['welcome'])
        this.invalidLogin = false
      },
      error => {
        console.log(error)
        this.invalidLogin = true
      }
    )
  }

}
