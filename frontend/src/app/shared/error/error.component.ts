import { Component, OnInit, signal } from '@angular/core';
import { ErrorService } from '../../service/error.service';
import { ErrorInt } from '../../model/error';
import { animate, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-error',
  imports: [],
  templateUrl: './error.component.html',
  styleUrl: './error.component.css',
  animations: [
    trigger('error', [
      transition(':enter', [
        style({ transform: 'translateX(200%)' }),
        animate('0.2s ease-in', style({ transform: 'translateX(0%)' }))
      ]),
      transition(':leave', [
        style({ opacity: 0, position: 'absolute' })
      ])
    ])
  ]
})
export class ErrorComponent implements OnInit {
  errors = signal<ErrorInt[]>([])

  constructor(private errorService: ErrorService) { }

  ngOnInit(): void {
    this.errors = this.errorService.getErrors();
  }



}
