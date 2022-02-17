import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { AuthenticationService } from '../service/authentication.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  topItems:MenuItem[] = [];
  bottomItems:MenuItem[] = [];

  constructor(
    public authService : AuthenticationService
  ) { }

  ngOnInit(): void {
    if (this.authService.isUserLoggedIn()){
      this.topItems = [
        {
          label: this.authService.getAuthenticatedUser().username,
          icon: "pi pi-user",
          styleClass : "ml-auto",
          items : [
            {
              label: "Logout",
              icon: "pi pi-sign-out",
              //routerLink: ""
            }
          ]
        }
      ]
      this.bottomItems = [
        {label:"Projektmenedzsment", routerLink:""},
        {label:"Munkaidőnyilvántartás", routerLink:"/worktime-register"},
        {label:"Pénzügyi adminisztráció", routerLink:""},
        {label:"Adminisztráció", routerLink:"", styleClass:'ml-auto'},
      ]
    }
  }

}
