import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../service/authentication.service';
import { WelcomeService } from '../service/welcome.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  public welcomeMessage : string = "";

  constructor(
    public authService : AuthenticationService,
    private welcomeService : WelcomeService,
  ) { }

  ngOnInit(): void {
  }

  //is this working?
  handleMessageRequest(){
    this.welcomeService.getCustomMessage().subscribe({
      next : (messageObject) => (this.welcomeMessage = messageObject.message),
      error : (e) => console.log(e)
    })
  }

}
