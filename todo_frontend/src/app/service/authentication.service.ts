import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs';
import { API_URL } from '../app.constants';

export const TOKEN = "token";
export const AUTHENTICATED_USER = "authenticatedUser"

export class TokenDto{
  constructor(public token:string){}
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  constructor(private http:HttpClient) { }

  login(username:string, password:string){
    return this.http.post<TokenDto>(`${API_URL}/authenticate`, {username, password}).pipe(map(
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

  getLoggedInUser():string{
    let username = sessionStorage.getItem(AUTHENTICATED_USER);
    if (username !== null){
      return username;
    }
    return "";
  }

  getToken():string{
    let token = sessionStorage.getItem(TOKEN)
    if (token !== null){
      return token;
    }
    return "";
  }
}
