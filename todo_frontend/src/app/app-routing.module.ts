import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ErrorComponent } from './error/error.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { RouteGuardService } from './service/route-guard.service';
import { TodoListComponent } from './todo-list/todo-list.component';
import { TodoComponent } from './todo/todo.component';
import { WelcomeComponent } from './welcome/welcome.component';

const routes: Routes = [
  { path:'', component: LoginComponent},
  { path:'logout', component: LogoutComponent, canActivate:[RouteGuardService]},
  { path:'welcome', component: WelcomeComponent, canActivate:[RouteGuardService]},
  { path:'todos', component: TodoListComponent, canActivate:[RouteGuardService]},
  { path:'todos/:id', component: TodoComponent, canActivate:[RouteGuardService]},
  { path:'login', component: LoginComponent},

  { path:'**', component: ErrorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
