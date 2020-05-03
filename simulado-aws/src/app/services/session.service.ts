import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor(private http: HttpClient) {

  }

  public getSession(): Observable<any> {
    return this.http.get('http://192.168.0.15:8080/session');
  }

  saveSession(currentSession: any): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    return this.http.post('http://192.168.0.15:8080/session', currentSession, httpOptions);
  }
}
