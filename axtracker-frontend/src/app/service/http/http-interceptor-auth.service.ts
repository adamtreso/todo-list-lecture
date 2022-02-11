import { HttpEvent, HttpHandler, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JwtAuthenticationService } from '../jwt-authentication.service';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorAuthService {
  constructor(private authService : JwtAuthenticationService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> { //req cannot be modifyed
    if (this.authService.getAuthenticatedToken() && this.authService.getAuthenticatedUser()){
      //if both have a vaild value then the user is logged in so we can add the header
      req = req.clone({
        setHeaders: {
          Authorization : this.authService.getAuthenticatedToken()
        }
      })
    }

    return next.handle(req);
  }
}
