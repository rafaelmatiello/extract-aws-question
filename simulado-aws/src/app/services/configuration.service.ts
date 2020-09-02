import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {

  private url = '';

  constructor() {
    if (environment.apiUrl !== '') {
      this.url = environment.apiUrl;
    } else {
      this.url = window.location.protocol + '//' + window.location.host + ':8080';
    }

  }

  public String;

  getUrlBackend(): string {
    return this.url;
  }


}
