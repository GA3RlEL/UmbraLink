import { Component, ElementRef, inject, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { RestorePasswordService } from '../../service/restore-password.service';

@Component({
  selector: 'app-change-password',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './change-password.component.html',
  styleUrl: './change-password.component.css'
})
export class ChangePasswordComponent implements OnInit {
  @ViewChild('progressBar', { static: true }) progressBar!: ElementRef<HTMLProgressElement>
  private restorePasswordService = inject(RestorePasswordService);
  private router = inject(Router);
  private activeRoute = inject(ActivatedRoute);
  private passwordStrenght: 'very-weak' | 'weak' | 'mediocre' | 'strong' | 'very-strong' | null = null;
  passwordStrenghtValue = 0;
  form = new FormGroup({
    newPassword: new FormControl('', [Validators.required])
  })
  private token: string = '';
  isSuccessChangePassword = false;

  ngOnInit(): void {
    this.activeRoute.params.subscribe(params => {
      const token = params['token']
      this.token = token;
      this.restorePasswordService.checkIsTokenValid(token).subscribe();
    })
  }


  onSubmit() {
    if (this.token != '' && this.form.value['newPassword']) {
      this.restorePasswordService.updatePassword(this.token, this.form.value['newPassword']).subscribe({
        next: () => {
          this.isSuccessChangePassword = true
          this.form.value['newPassword'] = '';
          this.form.disable()
        }
      })
    }
  }


  onChange(event: Event) {
    console.log(this.form.value['newPassword']);
    if (this.form.value['newPassword'] != null) {
      this.checkComplexity(this.form.value['newPassword'])
    }

  }

  checkComplexity(password: string) {
    let strength = 0;

    if (password.length >= 8) strength += 20;
    if (/[a-z]/.test(password)) strength += 20;
    if (/[A-Z]/.test(password)) strength += 20;
    if (/[0-9]/.test(password)) strength += 20;
    if (/[@$!%*?&#]/.test(password)) strength += 20;

    this.passwordStrenghtValue = strength


    switch (this.passwordStrenghtValue) {
      case 20:
        this.passwordStrenght = 'very-weak'
        break;
      case 40:
        this.passwordStrenght = 'weak'
        break;
      case 60:
        this.passwordStrenght = 'mediocre'
        break;
      case 80:
        this.passwordStrenght = 'strong'
        break;
      case 100:
        this.passwordStrenght = 'very-strong';
        break;
      default:
        this.passwordStrenght = 'very-weak'
        break;
    }

    this.progressBar.nativeElement.className = ''
    this.progressBar.nativeElement.classList.add(`progress-${this.passwordStrenght}`)
  }

}
