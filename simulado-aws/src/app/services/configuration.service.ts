import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {

  private url = 'http://note-rafael:8080';

  constructor() {
    this.url = window.location.protocol + '//' + window.location.host + ':8080';
  }

  public String;

  getUrlBackend(): string {
    return this.url;
  }
}
