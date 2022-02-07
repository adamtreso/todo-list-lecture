import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { WelcomeDataService } from '../service/data/welcome-data.service';
import { JwtAuthenticationService } from '../service/jwt-authentication.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  customWelcomeMessage : string

  //Activated Route
  constructor(
    private route:ActivatedRoute,
    private welcomeService : WelcomeDataService,
    private authSerice : JwtAuthenticationService){

    }

  ngOnInit() {
  }

  getWelcomeMessageWithName(){
    //subscribing for the observable to do smth when it arrives - async call
    this.welcomeService.executeServiceWithParamater(this.authSerice.getAuthenticatedUser()).subscribe(
      response => {
        this.customWelcomeMessage = response.message;
      },
      error => {
        this.customWelcomeMessage = error.error.message;
      }
    );
  }
}
