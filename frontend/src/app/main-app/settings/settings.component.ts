import { AfterViewInit, Component, OnDestroy, OnInit, signal } from '@angular/core';
import { User } from '../../model/user';
import { AppService } from '../../service/app.service';
import { animate, style, transition, trigger } from '@angular/animations';
import { NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-settings',
  imports: [NgIf, FormsModule],
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
  isOpen = false;
  isEditUsername = false;
  isEditPassword = false;
  username: string = ''
  password = '';

  constructor(private appService: AppService) { }

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

  editUsername() {
    this.isEditUsername = !this.isEditUsername;
    this.username = '';
  }

  editPassword() {
    this.isEditPassword = !this.isEditPassword;
    this.password = '';
  }

  updateUsername() {
    console.log("dupa");
    this.appService.updateUsername(this.username)?.subscribe();
    this.editUsername();
  }

}
