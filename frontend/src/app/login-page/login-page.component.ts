import { CssSelector } from '@angular/compiler';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-login-page',
  imports: [ReactiveFormsModule],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css',
})
export class LoginPageComponent {
  isLoginForm = true;
  loginFrom = new FormGroup({
    email: new FormControl('', [Validators.email, Validators.required]),
    password: new FormControl('', [Validators.required])
  })
  registerForm = new FormGroup({
    userName: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.email, Validators.required]),
    password: new FormControl('', [Validators.required]),
  })


  changeForm() {
    this.isLoginForm = !this.isLoginForm
    this.loginFrom.reset();
    this.registerForm.reset();
  }

  submitLoginForm() {
    console.log(this.loginFrom.value);
    console.log(this.loginFrom.errors);
    console.log(this.loginFrom);
  }

  submitRegisterForm() {
    console.log(this.registerForm.value);
  }

}
