import { Injectable } from '@angular/core';
import { CompatClient, Message, Stomp } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { MessageToSend } from '../model/conversation';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private readonly WS_URL = 'ws://localhost:8080/api/v1/ws'
  private stompClient: CompatClient | null = null;
  private isConnected = false;
  private messageSubject: Subject<MessageToSend> = new Subject<MessageToSend>();
  constructor() {
  }

  connect(): void {
    if (!this.stompClient?.connected) {
      this.stompClient = Stomp.client(this.WS_URL);
      this.stompClient.connect({}, () => {
        console.log('WebSocket connected');
        this.isConnected = true
        this.subscribeToTopic();
      });
    }

  }

  subscribeToTopic() {
    if (this.stompClient) {
      this.stompClient.subscribe('/topic', (message) => {
        const parsedMessage: MessageToSend = JSON.parse(message.body);
        this.messageSubject.next(parsedMessage);
      });
    }
  }

  getMessage(): Subject<MessageToSend> {
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