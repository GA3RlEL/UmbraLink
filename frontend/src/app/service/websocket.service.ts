import { Injectable } from '@angular/core';
import { CompatClient, Stomp } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private readonly WS_URL = 'ws://localhost:8080/api/v1/ws'
  private stompClient: CompatClient | null = null;
  private isConnected = false;
  private messageSubject: Subject<any> = new Subject<any>();
  constructor() {
  }

  connect(): void {
    this.stompClient = Stomp.client(this.WS_URL);
    this.stompClient.connect({}, () => {
      console.log('WebSocket connected');
      this.isConnected = true
      this.subscribeToTopic();
    });
  }

  subscribeToTopic() {
    if (this.stompClient) {
      this.stompClient.subscribe('/topic', (message) => {
        this.messageSubject.next(message.body);
      });
    }
  }

  getMessage(): Subject<any> {
    return this.messageSubject;
  }


  sendMessage(destination: string, message: any): void {
    if (this.stompClient && this.stompClient.connected && this.isConnected) {
      this.stompClient.send(destination, {}, JSON.stringify(message))
    }
  }


  disconnect(): void {
    if (this.stompClient) {
      this.stompClient.disconnect(() => {
        console.log("Disconnected from websocket");
        this.isConnected = false;
      })
    }
  }
}