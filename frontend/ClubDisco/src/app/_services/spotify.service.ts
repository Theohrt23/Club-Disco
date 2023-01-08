import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { AppConstants } from '../constant/app.constants';
import { SpotifyPlaylists } from './shared/spotify';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


@Injectable({
providedIn: 'root'
})

export class SpotifyService {

    constructor(private http: HttpClient) { }
  
    getSpotifyPlaylists(): Observable<SpotifyPlaylists> {
        let api = AppConstants.API_URL + `spotify/user-playlists`;  
        return this.http.get<SpotifyPlaylists>(api).pipe(retry(1), catchError(this.handleError));
    }

    getSpotifyProfil(): Observable<any> {
      let api = AppConstants.API_URL + `spotify/user-profil`;  
      return this.http.get<any>(api).pipe(retry(1), catchError(this.handleError));
    }

    getSpotifyFollowedArtists(): Observable<any> {
      let api = AppConstants.API_URL + `spotify/user-followed-artists`;  
      return this.http.get<any>(api).pipe(retry(1), catchError(this.handleError));
    }

    getSpotifyFavTracks(): Observable<any> {
      let api = AppConstants.API_URL + `spotify/user-fav-tracks`;  
      return this.http.get<any>(api).pipe(retry(1), catchError(this.handleError));
    }

    getSpotifyNewReleases(): Observable<any> {
      let api = AppConstants.API_URL + `spotify/new-releases`;  
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