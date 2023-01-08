import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppConstants } from '../constant/app.constants';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(credentials:any): Observable<any> {
    return this.http.post(AppConstants.AUTH_API + 'signin', {
      email: credentials.username,
      password: credentials.password
    }, httpOptions);
  }

  register(user:any): Observable<any> {
    return this.http.post(AppConstants.AUTH_API + 'signup', {
      displayName: user.displayName,
      email: user.email,
      password: user.password,
      matchingPassword: user.matchingPassword,
      socialProvider: 'LOCAL'
    }, httpOptions);
  }

  showAlert(message: String) {
    let alertPlaceholder = document.getElementById('alert_message')
    if (alertPlaceholder) {
      const wrapper = document.createElement('div')
      wrapper.innerHTML = [
        `   <div>${message}</div>`,
      ].join('')
      alertPlaceholder.append(wrapper)
    }
  }
}
