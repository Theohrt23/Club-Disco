import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { AppConstants } from '../constant/app.constants';
import { NONE_TYPE } from '@angular/compiler';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


@Injectable({
providedIn: 'root'
})

export class ConfigService {

    constructor(private http: HttpClient) { }
  
    setZippopotamConfig(config_id:any, country: any, zip_code:any): Observable<any> {
        let api = `${AppConstants.API_URL}config/${config_id}/${country}/${zip_code}`;
        return this.http.put<any>(api,null).pipe(retry(1), catchError(this.handleError));
    }

    setWeatherConfig(config_id:any, city: any): Observable<any> {
      let api = `${AppConstants.API_URL}config/${config_id}/${city}`;
      return this.http.put<any>(api,null).pipe(retry(1), catchError(this.handleError));
  }

    getUserConfig(id: any): Observable<any> {
        let api = `${AppConstants.API_URL}config/${id}`;
        return this.http.get<any>(api).pipe(retry(1), catchError(this.handleError));
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