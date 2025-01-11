import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { loginRequest } from '../model/auth';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private BASEURL: string = "http://localhost:8080/api/v1"

  constructor(private http: HttpClient) { }

  login(loginRequest: loginRequest) {
    this.http.post(this.BASEURL + "/login", loginRequest).subscribe({
      next(value) {
        console.log(value);
      },
      error(err) {
        console.error(err)
      },
    })
  }
}
