import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  baseUrl = "http://localhost:9000/api/v1/notifications";

  constructor(private httpClient:HttpClient) { }
  
  getAllNotification(){
    const url = `${this.baseUrl}`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.get(url, requestOptions);
  }
  
  private getRequestOptions() {
    const httpHeaders = new HttpHeaders({
      Authorization: 'Bearer ' + localStorage.getItem('token'),
    });
    let requestOptions = {headers : httpHeaders}
    return requestOptions;
  }
}
