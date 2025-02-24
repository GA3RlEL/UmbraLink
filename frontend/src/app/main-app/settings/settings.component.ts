import { Component, OnInit, signal } from '@angular/core';
import { User } from '../../model/user';
import { AppService } from '../../service/app.service';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-settings',
  imports: [NgIf],
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
export class SettingsComponent implements OnInit {
  user = signal<User | null>(null);
  isOpen = true;

  constructor(private appService: AppService) { }

  changeIsOpen() {
    this.isOpen = !this.isOpen;
  }

  ngOnInit(): void {
    this.user = this.appService.getUser();
  }

}
