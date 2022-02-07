import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators'
import { API_URL } from '../app.constants';

export const TOKEN = "token";
export const AUTHENTICATED_USER = "authenticatedUser"

export class JwtTokenBean{
  constructor(public token:string){}
}

@Injectable({
  providedIn: 'root'
})
export class JwtAuthenticationService {

  constructor(private http:HttpClient) { }

  authenticate(username, password){
    ///{username, password} will be the body, and its a json
    return this.http.post<JwtTokenBean>(`${API_URL}/authenticate`, {username, password}).pipe(map(
      data => {
        sessionStorage.setItem(AUTHENTICATED_USER, username);
        sessionStorage.setItem(TOKEN, `Bearer ${data.token}`);
        return data;
      }
    ))
  }

  logout(){
    sessionStorage.removeItem(AUTHENTICATED_USER);
    sessionStorage.removeItem(TOKEN);
  }
  
  isUserLoggedIn():boolean{
    let username = sessionStorage.getItem(AUTHENTICATED_USER);
    return !(username === null);
  }

  getAuthenticatedUser(){
    if (this.isUserLoggedIn()){
      return sessionStorage.getItem(AUTHENTICATED_USER)
    }
  }

  getAuthenticatedToken(){
    if (this.isUserLoggedIn()){
      return sessionStorage.getItem(TOKEN)
    }
    
  }

}
