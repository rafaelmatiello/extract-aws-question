import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(private http: HttpClient) {

  }

  public getQuestion(index: number): Observable<any> {
    return this.http.get('http://localhost:8080/questions/' + index);
  }

}
