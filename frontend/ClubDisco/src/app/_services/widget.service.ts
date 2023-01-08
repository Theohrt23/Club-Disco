import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { AppConstants } from '../constant/app.constants';
import { Widget } from './shared/widget';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


@Injectable({
providedIn: 'root'
})

export class WidgetService {

    constructor(private http: HttpClient) { }
  
    getUserWidgets(id: any): Observable<Widget> {
        let api = AppConstants.API_URL + `widget/currentWidgets/${id}`;  
        return this.http.get<Widget>(api).pipe(retry(1), catchError(this.handleError));
    }

    addWidgetToUser(user_id: any, widget_id: any): Observable<Widget> {
      let api = AppConstants.API_URL + `widget/${user_id}/${widget_id}`;  
      return this.http.get<Widget>(api).pipe(retry(1), catchError(this.handleError));
    }

    removeWidgetToUser(user_id: any, widget_id: any): Observable<void> {
      let api = AppConstants.API_URL + `widget/${user_id}/${widget_id}`;  
      return this.http.delete<void>(api).pipe(retry(1), catchError(this.handleError));
    }

    haveSpotify(id: any) {
      let api = AppConstants.API_URL + `widget/haveSpotify/${id}`;  
      return this.http.get<any>(api).pipe(catchError(this.handleError));
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