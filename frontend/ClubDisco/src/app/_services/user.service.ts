import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { AppConstants } from '../constant/app.constants';
import { User } from './shared/user';

const httpOptions = {
		  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
		};


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getPublicContent(): Observable<any> {
    return this.http.get(AppConstants.API_URL + 'all', { responseType: 'text' });
  }

  getUserBoard(): Observable<any> {
    return this.http.get(AppConstants.API_URL + 'user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(AppConstants.API_URL + 'mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(AppConstants.API_URL + 'admin', { responseType: 'text' });
  }

  getCurrentUser(): Observable<any> {
    return this.http.get(AppConstants.API_URL + 'user/me', httpOptions);
  }

  getAllUsers(): Observable<any> {
    let api = AppConstants.API_URL + `user/all`;  
    return this.http.get<any>(api).pipe(retry(1), catchError(this.handleError));
  }

  deleteUser(id: any): Observable<any> {
    let api = AppConstants.API_URL + id;  
    return this.http.delete<any>(api).pipe(retry(1), catchError(this.handleError));
  }

  upgradeUser(id: any): Observable<any> {
    let api = AppConstants.API_URL + `user/upgrade/` + id;  
    return this.http.put<any>(api,null).pipe(retry(1), catchError(this.handleError));
  }

  editUser(user: User,id:any): Observable<any> {
    let api = AppConstants.API_URL + id; 
    return this.http.put<User>(api, user).pipe(catchError(this.handleError));
  }



  handleError(error: HttpErrorResponse) {
    let msg = '';
    if (error.error instanceof ErrorEvent) {
      msg = error.error.message;
    } else {
      msg = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(msg);
  }
}

