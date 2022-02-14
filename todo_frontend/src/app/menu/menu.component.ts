import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { AuthenticationService } from '../service/authentication.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  items:MenuItem[] = [];

  constructor(
    public authService : AuthenticationService
  ) { }

  ngOnInit(): void {
    if (this.authService.isUserLoggedIn()){
      this.items = [
        {label:"Welcome", routerLink:"/welcome"},
        {label:"Todos", routerLink:"/todos"}
      ]
    }
  }

}
