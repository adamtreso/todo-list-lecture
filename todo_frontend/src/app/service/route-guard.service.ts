import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class RouteGuardService {

  constructor(private roter : Router, private authService : AuthenticationService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) : boolean{
    if (this.authService.isUserLoggedIn())
      return true;
    this.roter.navigate(['login']);
    return false;
  }
}
