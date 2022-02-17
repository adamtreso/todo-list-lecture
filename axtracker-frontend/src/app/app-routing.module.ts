import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ErrorComponent } from './error/error.component';
import { LoginComponent } from './login/login.component';
import { RouteGuardService } from './service/route-guard.service';
import { WelcomeComponent } from './welcome/welcome.component';
import { WorktimeRegisterComponent } from './worktime-register/worktime-register.component';

const routes: Routes = [
  { path:'', component: LoginComponent},
  { path:'welcome', component: WelcomeComponent, canActivate:[RouteGuardService]},
  { path:'worktime-register', component: WorktimeRegisterComponent, canActivate:[RouteGuardService]},

  { path:'**', component: ErrorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
