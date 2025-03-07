import { Injectable, signal, Signal } from '@angular/core';
import { ErrorInt } from '../model/error';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {
  private errors = signal<ErrorInt[]>([])

  addError(error: ErrorInt) {
    this.errors().push(error)
  }

  removeError(errorId: String) {
    this.errors.set(this.errors().filter(err => err.id != errorId));
  }

  getErrors() {
    return this.errors;
  }

  constructor() { }
}
