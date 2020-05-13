import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ConfigurationService} from './configuration.service';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor(private http: HttpClient,
              private configurationService: ConfigurationService) {

  }

  public getSession(): Observable<any> {
    return this.http.get(this.configurationService.getUrlBackend() + '/session');
  }

  saveSession(currentSession: any): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    return this.http.post(this.configurationService.getUrlBackend() + '/session', currentSession, httpOptions);
  }
}
