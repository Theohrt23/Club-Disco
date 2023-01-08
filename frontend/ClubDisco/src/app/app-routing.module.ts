import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { DashboardComponentComponent } from './dashboard-component/dashboard-component.component';
import { LoginComponent } from './login/login.component';
import { ProfilComponent } from './profil/profil.component';
import { SignupComponentComponent } from './signup-component/signup-component.component';

const routes: Routes = [
{ path: 'dashboard', component: DashboardComponentComponent },
{ path: 'login', component: LoginComponent },
{ path: 'signup', component: SignupComponentComponent },
{ path: 'admin', component: AdminComponent },
{ path: 'profile', component: ProfilComponent },
{ path: '', redirectTo: '/login', pathMatch: 'full' }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
