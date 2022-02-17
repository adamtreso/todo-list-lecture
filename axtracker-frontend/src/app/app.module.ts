import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuComponent } from './menu/menu.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpInterceptorAuthService } from './service/http-interceptor-auth.service';
import {ButtonModule} from 'primeng/button';
import { LoginComponent } from './login/login.component';
import { WelcomeComponent } from './welcome/welcome.component';
import {TabViewModule} from 'primeng/tabview';
import {MessagesModule} from 'primeng/messages';
import { FormsModule } from '@angular/forms';
import { ErrorComponent } from './error/error.component';
import { InputTextModule } from "primeng/inputtext";
import {TableModule} from 'primeng/table';
import {CheckboxModule} from 'primeng/checkbox';
import { BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CalendarModule} from 'primeng/calendar';
import {InputSwitchModule} from 'primeng/inputswitch';
import {TabMenuModule} from 'primeng/tabmenu';
import {PanelMenuModule} from 'primeng/panelmenu';
import {MenubarModule} from 'primeng/menubar';
import { WorktimeRegisterComponent } from './worktime-register/worktime-register.component';



@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    LoginComponent,
    WelcomeComponent,
    ErrorComponent,
    WorktimeRegisterComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    ButtonModule,
    TabViewModule,
    MessagesModule,
    InputTextModule,
    TableModule,
    CheckboxModule,
    BrowserAnimationsModule,
    CalendarModule,
    InputSwitchModule,
    TabMenuModule,
    PanelMenuModule,
    MenubarModule,
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorAuthService, multi:true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
