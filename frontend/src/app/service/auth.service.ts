import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { loginRequest, registerRequest, tokenResponse } from '../model/auth';
import { Router } from '@angular/router';
import { ErrorService } from './error.service';
import { v4 as uuidv4 } from 'uuid'
import { BASEURL } from '../shared/helper/consts';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isAuthReq: Subject<boolean> = new Subject<boolean>();

  constructor(private http: HttpClient, private router: Router, private errorService: ErrorService) { }

  getIsAuthSubjec() {
    return this.isAuthReq;
  }

  login(loginRequest: loginRequest) {
    this.isAuthReq.next(true);
    this.http.post<tokenResponse>(BASEURL + "/login", loginRequest).subscribe({
      next: (response) => {
        const token = response.token;
        localStorage.setItem('authToken', token);
        this.router.navigate(['/app'])
        this.isAuthReq.next(false);
      },
      error: (err) => {
        this.errorService.addError({ id: uuidv4(), errorCode: err.error.errorCode, errorMessage: err.error.message })
        this.isAuthReq.next(false);
      },

    })
  }


  register(registerRequest: registerRequest) {
    this.http.post<tokenResponse>(BASEURL + "/register", registerRequest).subscribe({
      next: response => {
        const token = response.token;
        localStorage.setItem('authToken', token);
        this.router.navigate(['/app'])
        this.isAuthReq.next(false);
      },
      error: err => {
        this.errorService.addError({ id: uuidv4(), errorCode: err.error.errorCode, errorMessage: err.error.message })
        this.isAuthReq.next(false);
      },
    })
  }

  get authReq() {
    return this.isAuthReq;
  }


}
