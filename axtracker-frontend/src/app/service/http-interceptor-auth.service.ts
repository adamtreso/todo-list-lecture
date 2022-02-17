import { HttpEvent, HttpHandler, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorAuthService {
  constructor(private authService : AuthenticationService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    try{
      let token = this.authService.getToken();
      req = req.clone({
        headers: req.headers.set('Authorization', token)
      })
    }catch(error){}
    return next.handle(req);
  }
}
