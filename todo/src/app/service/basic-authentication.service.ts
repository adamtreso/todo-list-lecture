import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators'
import { API_URL } from '../app.constants';

export class AuthenticationBean{
  constructor(public message:string){}
}

@Injectable({
  providedIn: 'root'
})
export class BasicAuthenticationService {

  constructor(private http:HttpClient) { }

  authenticate(username, password){
    let basicAuthHeaderString = 'Basic ' + window.btoa(username + ':' + password)

    let headers = new HttpHeaders({
      Authorization : basicAuthHeaderString
    })

    return this.http.get<AuthenticationBean>(`${API_URL}/basicauth`, {headers}).pipe(map(
      data => {
        sessionStorage.setItem('authenticatedUser', username);
        sessionStorage.setItem('token', basicAuthHeaderString);
        return data;
      }
    ))
  }

  logout(){
    sessionStorage.removeItem('authenticatedUser');
    sessionStorage.removeItem('token');
  }
  
  isUserLoggedIn():boolean{
    let username = sessionStorage.getItem('authenticatedUser');
    return !(username === null);
  }

  getAuthenticatedUser(){
    if (this.isUserLoggedIn()){
      return sessionStorage.getItem('authenticatedUser')
    }
  }

  getAuthenticatedToken(){
    if (this.isUserLoggedIn()){
      return sessionStorage.getItem('token')
    }
    
  }

}
