import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConnectableObservable, map } from 'rxjs';
import jwt_decode from 'jwt-decode';
import { API_URL } from '../app.constants';

export const TOKEN = "token";
export const AUTHENTICATED_USER = "authenticatedUser"

export class TokenDto{
  constructor(public token:string){}
}

export class UserDto{
  constructor(
    public id : number,
    public username : string,
  ){}
}

class JwtBody{
  constructor(
    public sub : string,
    public iss : string,
    public exp : number,
    public iat : number,
    public user : UserDto
  ){}
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  constructor(private http:HttpClient) { }

  login(username:string, password:string){
    return this.http.post<TokenDto>(`${API_URL}/authenticate`, {username, password}).pipe(map(
      data => {
        sessionStorage.setItem(TOKEN, `Bearer ${data.token}`);
        let jwtBody = <JwtBody> jwt_decode(data.token);
        sessionStorage.setItem(AUTHENTICATED_USER, JSON.stringify(jwtBody.user));
        return data;
      }
    ))
  }

  logout() : void{
    sessionStorage.removeItem(AUTHENTICATED_USER);
    sessionStorage.removeItem(TOKEN);
  }

  isUserLoggedIn():boolean{
    let userString = sessionStorage.getItem(AUTHENTICATED_USER);
    return !(userString === null);
  }

  getAuthenticatedUser() : UserDto{
    let userString = sessionStorage.getItem(AUTHENTICATED_USER);
    if (userString === null){
      throw new Error('There is no authenticated user');
    }
    return JSON.parse(userString);
  }

  getToken():string{
    let token = sessionStorage.getItem(TOKEN)
    if (token === null){
      throw new Error('There is no token')
    }
    return token;
  }
}
