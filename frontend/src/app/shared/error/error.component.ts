import { Component, inject, Input } from '@angular/core';
import { ErrorService } from '../../service/error.service';
import { ErrorInt } from '../../model/error';

@Component({
  selector: 'app-error',
  imports: [],
  templateUrl: './error.component.html',
  styleUrl: './error.component.css',
})
export class ErrorComponent {
  @Input({ required: true }) error!: ErrorInt
  timer: ReturnType<typeof setTimeout> | null = null;
  value = 100
  errorService = inject(ErrorService)

  constructor() {
    this.setTimer();
  }

  setTimer() {
    this.timer = setInterval(() => {
      if (this.value > 0) {
        this.value -= 1;
      } else {
        this.removeError();
      }
    }, 30);
  }

  stopInterval() {
    if (this.timer) {
      clearInterval(this.timer);
    }
  }


  removeError() {
    if (this.timer) {
      clearInterval(this.timer)
      if (this.error) {
        this.errorService.removeError(this.error.id);
      }
    }

  }






}
