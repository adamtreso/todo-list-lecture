import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL } from '../app.constants';
import { AuthenticationService } from './authentication.service';

export class MessageDto{
  constructor(
    public message:string
  ){}
}

@Injectable({
  providedIn: 'root'
})
export class WelcomeService {

  constructor(
    private http:HttpClient,
    private authService : AuthenticationService
    ) { }

  getCustomMessage(){
    return this.http.get<MessageDto>(`${API_URL}/hello-world/path-variable/${this.authService.getLoggedInUser()}`)
  }
}
