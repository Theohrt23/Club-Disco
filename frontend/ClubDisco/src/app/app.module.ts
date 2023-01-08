import { NgModule } from '@angular/core';
import { ReactiveFormsModule,FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import {DragDropModule} from '@angular/cdk/drag-drop';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponentComponent } from './dashboard-component/dashboard-component.component';
import { SignupComponentComponent } from './signup-component/signup-component.component';
import { LoginComponent } from './login/login.component';

import {  authInterceptorProviders } from './_helpers/auth.interceptor';
import { AdminComponent } from './admin/admin.component';
import { ProfilComponent } from './profil/profil.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponentComponent,
    SignupComponentComponent,
    LoginComponent,
    AdminComponent,
    ProfilComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    DragDropModule,
    ReactiveFormsModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
