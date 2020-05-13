import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {ConfigurationService} from './configuration.service';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  constructor(private http: HttpClient,
              private configurationService: ConfigurationService) {
  }

  public getStatistics(): Observable<any> {
    return this.http.get(this.configurationService.getUrlBackend() + '/statistics');
  }
}
