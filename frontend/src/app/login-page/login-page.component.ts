import { animate, state, style, transition, trigger } from '@angular/animations';
import { CssSelector } from '@angular/compiler';
import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../service/auth.service';
import { loginRequest, registerRequest } from '../model/auth';
import { Router } from '@angular/router';
import { HealthCheckService } from '../service/health-check.service';

@Component({
  selector: 'app-login-page',
  imports: [ReactiveFormsModule],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css',
  animations: [
    trigger('changeVisibility', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('1s ease-in', style({ opacity: 1 }))
      ]),
      transition(':leave', [
        style({ opacity: 0, position: 'absolute' })
      ])
    ])
  ]
})
export class LoginPageComponent {
  isLoginForm = true;
  private healthCheck = inject(HealthCheckService)
  loginFrom = new FormGroup({
    email: new FormControl('', [Validators.email, Validators.required]),
    password: new FormControl('', [Validators.required])
  })
  registerForm = new FormGroup({
    userName: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.email, Validators.required]),
    password: new FormControl('', [Validators.required]),
  })

  error = ''

  constructor(private auth: AuthService) { }


  changeForm() {
    this.isLoginForm = !this.isLoginForm
    this.loginFrom.reset();
    this.registerForm.reset();
  }

  submitLoginForm() {
    const loginData: loginRequest = {
      email: this.loginFrom.value['email']!,
      password: this.loginFrom.value['password']!
    }

    this.auth.login(loginData);


  }

  submitRegisterForm() {
    const registerData: registerRequest = {
      email: this.registerForm.value['email']!,
      password: this.registerForm.value['password']!,
      username: this.registerForm.value['userName']!
    }
    this.auth.register(registerData);


  }

}
