import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { loginRequest, registerRequest, tokenResponse } from '../model/auth';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private BASEURL: string = "http://localhost:8080/api/v1"

  constructor(private http: HttpClient, private router: Router) { }

  login(loginRequest: loginRequest) {
    this.http.post<tokenResponse>(this.BASEURL + "/login", loginRequest).subscribe({
      next: (response) => {
        const token = response.token;
        localStorage.setItem('authToken', token);
        this.router.navigate(['/app'])
      },
      error: (err) => {
        console.error(err)
      },
    })
  }

  register(registerRequest: registerRequest) {
    this.http.post<tokenResponse>(this.BASEURL + "/register", registerRequest).subscribe({
      next: response => {
        const token = response.token;
        localStorage.setItem('authToken', token);
        this.router.navigate(['/app'])
      },
      error: err => {
        console.error(err);
      }
    })
  }


}
