import { Injectable, signal, Signal } from '@angular/core';
import { Subject } from 'rxjs';
import { ErrorInt } from '../model/error';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {
  private errors = signal<ErrorInt[]>([{ errorCode: 401, errorMessage: "XD" }])

  addError(message: ErrorInt) {
    this.errors().push(message)
  }

  getErrors() {
    return this.errors;
  }

  constructor() { }
}
