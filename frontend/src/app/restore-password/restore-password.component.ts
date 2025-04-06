import { trigger, transition, style, animate } from '@angular/animations';
import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ErrorService } from '../service/error.service';
import { RestorePasswordService } from '../service/restore-password.service';

@Component({
  selector: 'app-restore-password',
  imports: [ReactiveFormsModule],
  templateUrl: './restore-password.component.html',
  styleUrl: './restore-password.component.css',
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
export class RestorePasswordComponent implements OnInit {
  private errorService = inject(ErrorService)
  private restorePasswordService = inject(RestorePasswordService)
  isSendingRequest = false;
  message = ''
  isReqSuccess = false;

  form = new FormGroup({
    email: new FormControl('', [Validators.email, Validators.required])
  })

  ngOnInit(): void {
    this.restorePasswordService.getRestoreReq().subscribe(value => {
      this.isSendingRequest = value;
    })
    this.restorePasswordService.getIsReqSuccess().subscribe((v) => {
      if (v === true) {
        this.message = ''
        this.isReqSuccess = true;
      } else {
        this.isReqSuccess = false;
      }

    })
  }

  sendRestorePassword() {
    if (this.form.value['email'] != null) {
      this.isSendingRequest = true;
      this.restorePasswordService.sendRestorePasswordRequest(this.form.value['email'])
    }
  }

}
