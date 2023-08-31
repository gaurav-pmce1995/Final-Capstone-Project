import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserProfileService {
  baseUrl = "http://localhost:9000/api/v1/user-profile"

  constructor(private httpClient: HttpClient) { }
  

//creation of model class

  private getRequestOptions() {
    const httpHeaders = new HttpHeaders({
      Authorization: 'Bearer ' + localStorage.getItem('token'),
    });
    let requestOptions = { headers: httpHeaders }
    return requestOptions;
  }

  addUserProfile(userProfile:any) {
    const url = `${this.baseUrl}`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.post(url, userProfile, requestOptions);
  }

  getUserProfile(){
    const url = `${this.baseUrl}/get-user`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.get(url,requestOptions);
  }

  updateUserProfile( userProfile: any){
    const url = `${this.baseUrl}/update-user`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.put(url,userProfile,requestOptions);
  }

  deleteUserProfile(){
    const url = `${this.baseUrl}/delete`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.delete(url,requestOptions );
  }

// to be checked how to pass multipart file
  uploadImage(formData:FormData){
    const url = `${this.baseUrl}/upload`;
    const requestOptions = this.getRequestOptions();
    return this.httpClient.post(url,formData,requestOptions);
  }
}
