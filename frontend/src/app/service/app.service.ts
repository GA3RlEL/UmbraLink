import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tokenResponse } from '../model/auth';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class AppService {
  private BASEURL: string = "http://localhost:8080/api/v1"

  constructor(private http: HttpClient) {
  }

  getUserDetails() {
    const token = localStorage.getItem("authToken");
    if (token) {
      return this.http.get<User>(this.BASEURL + "/user/me", {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      })
    } else {
      console.error("Token was not found");
      return null;
    }


  }
}
