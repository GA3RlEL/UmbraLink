import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { loginRequest, registerRequest, tokenResponse } from '../model/auth';
import { Router } from '@angular/router';
import { ErrorService } from './error.service';
import { v4 as uuidv4 } from 'uuid'
import { HealthCheckService } from './health-check.service';
import { BASEURL } from '../shared/helper/consts';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private router: Router, private errorService: ErrorService) { }

  login(loginRequest: loginRequest) {

    this.http.post<tokenResponse>(BASEURL + "/login", loginRequest).subscribe({
      next: (response) => {
        const token = response.token;
        localStorage.setItem('authToken', token);
        this.router.navigate(['/app'])
      },
      error: (err) => {
        console.log(err);
        this.errorService.addError({ id: uuidv4(), errorCode: err.error.errorCode, errorMessage: err.error.message })
      },
    })
  }

  register(registerRequest: registerRequest) {
    this.http.post<tokenResponse>(BASEURL + "/register", registerRequest).subscribe({
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
