import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuComponent } from './menu/menu.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpInterceptorAuthService } from './service/http-interceptor-auth.service';
import {MenubarModule} from 'primeng/menubar'
import {ButtonModule} from 'primeng/button';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { WelcomeComponent } from './welcome/welcome.component';
import {TabViewModule} from 'primeng/tabview';
import {MessagesModule} from 'primeng/messages';
import { FormsModule } from '@angular/forms';
import { ErrorComponent } from './error/error.component';
import { InputTextModule } from "primeng/inputtext";
import { TodoListComponent } from './todo-list/todo-list.component';
import {TableModule} from 'primeng/table';
import {CheckboxModule} from 'primeng/checkbox';
import { BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { TodoComponent } from './todo/todo.component';
import {CalendarModule} from 'primeng/calendar';
import {InputSwitchModule} from 'primeng/inputswitch';



@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    LoginComponent,
    LogoutComponent,
    WelcomeComponent,
    ErrorComponent,
    TodoListComponent,
    TodoComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    MenubarModule,
    ButtonModule,
    TabViewModule,
    MessagesModule,
    InputTextModule,
    TableModule,
    CheckboxModule,
    BrowserAnimationsModule,
    CalendarModule,
    InputSwitchModule,
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorAuthService, multi:true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
