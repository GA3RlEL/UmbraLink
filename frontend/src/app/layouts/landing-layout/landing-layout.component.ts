import { Component, inject, InjectFlags, OnInit, signal } from '@angular/core';
import { NavBarComponent } from "../../shared/nav-bar/nav-bar.component";
import { RouterModule } from '@angular/router';
import { ErrorComponent } from "../../shared/error/error.component";
import { ErrorService } from '../../service/error.service';
import { ErrorInt } from '../../model/error';
import { animate, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-landing-layout',
  imports: [NavBarComponent, RouterModule, ErrorComponent],
  templateUrl: './landing-layout.component.html',
  styleUrl: './landing-layout.component.css',
  animations: [
    trigger('error', [
      transition(':enter', [
        style({ transform: 'translateX(200%)' }),
        animate('0.2s ease-in', style({ transform: 'translateX(0%)' }))
      ]),
      transition(':leave', [
        style({ transform: 'translateX(0%)' }),
        animate('0.2s ease-in', style({ transform: 'translateX(200%)' }))
      ])
    ])
  ]
})
export class LandingLayoutComponent implements OnInit {
  private errorService = inject(ErrorService)
  errors = signal<ErrorInt[]>([])

  ngOnInit(): void {
    this.errors = this.errorService.getErrors();
  }

  remove(errorId: string) {
    this.errorService.removeError(errorId)
  }




}
