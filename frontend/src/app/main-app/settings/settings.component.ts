import { Component, ElementRef, OnDestroy, OnInit, signal, ViewChild } from '@angular/core';
import { User } from '../../model/user';
import { AppService } from '../../service/app.service';
import { animate, style, transition, trigger } from '@angular/animations';
import { NgIf } from '@angular/common';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { WebsocketService } from '../../service/websocket.service';

@Component({
  selector: 'app-settings',
  imports: [NgIf, FormsModule, ReactiveFormsModule],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css',
  animations: [
    trigger('openClose', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('0.2s', style({ opacity: 1 }))
      ]),
      transition(':leave', [
        animate('0.2s', style({ opacity: 0 }))
      ])
    ])]
})
export class SettingsComponent implements OnInit, OnDestroy {
  user = signal<User | null>(null);
  isOpen = true;
  isEditUsername = false;
  isEditPassword = false;
  isUploadingPhoto = false;
  username: string = ''
  newPasswordGroup: FormGroup = new FormGroup({
    oldPassword: new FormControl('', [Validators.required]),
    newPassword: new FormControl('', [Validators.required]),
  })
  file!: Blob;
  photo: string | null | ArrayBuffer | undefined = '';
  @ViewChild("selectPhotoInput", { static: true }) selectPhotoInput!: ElementRef<HTMLInputElement>

  constructor(private appService: AppService, private activeRouter: ActivatedRoute, private router: Router, private websocketService: WebsocketService
  ) { }

  ngOnDestroy(): void {
    this.isEditUsername = false;
    this.isOpen = true;
  }

  ngOnInit(): void {
    this.user = this.appService.getUser();
  }

  changeIsOpen() {
    this.isOpen = !this.isOpen;
  }

  logout() {
    if (localStorage.getItem('authToken')) {
      localStorage.removeItem('authToken')
    }
    this.websocketService.disconnect();
    this.router.navigate(["/"])
  }

  back() {
    this.router.navigate(['.'], { relativeTo: this.activeRouter.parent })
  }

  readFile(event: any) {
    if (event.target.files) {
      this.file = event.target.files[0];
      const reader = new FileReader();

      reader.onload = (e: any) => {
        this.photo = e.target.result;
      };

      reader.readAsDataURL(this.file);
    }
  }

  onCancel() {
    this.photo = null;
    this.file = new Blob();
    this.selectPhotoInput.nativeElement.value = '';

  }

  saveAvatar() {
    if (this.file && this.photo != null) {
      const formData: FormData = new FormData();
      const email = this.user()?.email;
      formData.append('file', this.file, `profile${email}`);
      this.isUploadingPhoto = true
      this.appService.saveAvatar(formData)?.subscribe({
        next: () => {
          this.onCancel();
        }, error: (err) => {
          console.error(err)
        }, complete: () => {
          this.isUploadingPhoto = false;
        }
      });
    }
  }


  editUsername() {
    this.isEditUsername = !this.isEditUsername;
    this.username = '';
  }

  editPassword() {
    this.isEditPassword = !this.isEditPassword;
    this.newPasswordGroup.reset();
  }

  updateUsername() {
    this.appService.updateUsername(this.username)?.subscribe();
    this.editUsername();
  }

  updatePassword() {
    if (this.newPasswordGroup.valid) {
      const oldPassword = this.newPasswordGroup.controls['oldPassword'].value;
      const newPassword = this.newPasswordGroup.controls['newPassword'].value;
      this.appService.updatePassword(oldPassword, newPassword)?.subscribe(
        {
          next: value => {
            if (localStorage.getItem('authToken')) {
              localStorage.setItem('authToken', value.token);
            }
            this.editPassword();
          },
          error: err => {
            console.error(err)
          }
        }
      )
    }
  }

}
