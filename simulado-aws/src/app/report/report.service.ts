import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigurationService} from '../services/configuration.service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(private http: HttpClient,
              private configurationService: ConfigurationService) { }


  public getReport(index: any): Observable<any> {
    return this.http.get(this.configurationService.getUrlBackend() + '/reports/' + index);
  }
}
