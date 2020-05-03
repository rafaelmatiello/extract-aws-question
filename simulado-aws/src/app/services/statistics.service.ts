import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  constructor(private http: HttpClient) {
  }

  public getStatistics(): Observable<any> {
    return this.http.get('http://192.168.0.15:8080/statistics');
  }
}
