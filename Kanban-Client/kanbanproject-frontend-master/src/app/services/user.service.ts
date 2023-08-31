import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  private tokenKey = 'token';
  isLoggedIn = false;

  constructor(private httpClient:HttpClient) { }

  login() {
    this.isLoggedIn = true;
  }

  logout() {
    this.isLoggedIn = false;
    localStorage.removeItem("token");
  }

  authorize() {
    if(this.getToken()){
    return true;
    }
    return false;
  }

  setToken(token: string) {
    localStorage.setItem(this.tokenKey, token);
  }

  getToken() {
    return localStorage.getItem(this.tokenKey);
  }

  baseUrl = "http://localhost:9000/api/v1/users";

  registerUser(signupData:any){
    const url = `${this.baseUrl}/signup`
    return this.httpClient.post(url, signupData);
  }

  loginCheck(loginData:any){
    const url=`${this.baseUrl}/login`
    return this.httpClient.post(url, loginData);
  }



}

