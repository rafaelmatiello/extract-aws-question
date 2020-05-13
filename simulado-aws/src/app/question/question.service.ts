import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ConfigurationService} from '../services/configuration.service';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(private http: HttpClient,
              private configurationService: ConfigurationService) {

  }

  public getQuestion(index: number): Observable<any> {
    return this.http.get(this.configurationService.getUrlBackend() + '/questions/' + index);
  }

  getCount(): Observable<any> {
    return this.http.get(this.configurationService.getUrlBackend() + '/questions/count');
  }

  getIndexWrong() {
    return this.http.get(this.configurationService.getUrlBackend() + '/questions/wrong');
  }
}
