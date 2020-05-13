import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ConfigurationService} from './configuration.service';

@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  constructor(private http: HttpClient,
              private configurationService: ConfigurationService) {
  }

  saveAnswer(answer: any): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    return this.http.post(this.configurationService.getUrlBackend() + '/answers/' + answer.index, answer, httpOptions);
  }
}
