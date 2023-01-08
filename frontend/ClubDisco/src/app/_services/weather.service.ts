import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { AppConstants } from '../constant/app.constants';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


@Injectable({
providedIn: 'root'
})

export class WeatherService {

    constructor(private http: HttpClient) { }
  
    getWeatherOfCity(country: any): Observable<any> {
        let api = AppConstants.API_URL + `weather/` + country;  
        return this.http.get<any>(api).pipe(retry(1), catchError(this.handleError));
    }

    getWeatherOfCityFor7Days(country: any): Observable<any> {
        let api = AppConstants.API_URL + `weather/for7days/` + country;  
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