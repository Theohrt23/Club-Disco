import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { AppConstants } from '../constant/app.constants';
import { Nasa } from './shared/nasa';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


@Injectable({
providedIn: 'root'
})

export class NasaService {

    constructor(private http: HttpClient) { }
  
    getNasaPictures(): Observable<Nasa> {
        let api = AppConstants.API_URL + `nasa`;  
        return this.http.get<Nasa>(api).pipe(retry(1), catchError(this.handleError));
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