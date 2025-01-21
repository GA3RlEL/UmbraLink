import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private readonly WS_URL = 'ws://localhost:8080/api/v1/ws'
  private socket$: WebSocketSubject<any> | null = null;
  constructor() {
  }

  connect() {
    this.socket$ = webSocket(this.WS_URL)
    console.log("Connected");
    console.log(this.socket$);
  }

  onMessage(): Observable<any> {
    if (!this.socket$) {
      console.error("The connection wasn't established")
    }

    return this.socket$ as Observable<any>
  }

  sendMessage(message: any): void {
    if (!this.socket$) {
      console.error("The connection wasn't established")
    }
    this.socket$?.next(message);
  }


  disconnect() {
    this.socket$?.complete();
    this.socket$ = null;
  }
}