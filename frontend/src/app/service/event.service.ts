import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private readSubject: Subject<void> = new Subject();
  read$ = this.readSubject.asObservable();

  emitReadMessages() {
    this.readSubject.next();
  }
}
