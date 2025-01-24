import { Injectable } from '@angular/core';
import { CompatClient, Stomp } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { Message, ReadMessage } from '../model/conversation';
import { JsonPipe } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private readonly WS_URL = 'ws://localhost:8080/api/v1/ws'
  private stompClient: CompatClient | null = null;
  private isConnected = false;
  private messageSubject: Subject<Message> = new Subject<Message>();
  private readMessageSubject: Subject<ReadMessage> = new Subject<ReadMessage>();
  constructor() {
  }

  connect(): void {
    if (!this.stompClient?.connected) {
      this.stompClient = Stomp.client(this.WS_URL);
      this.stompClient.connect({}, () => {
        this.isConnected = true
        this.subscribeToTopic();
        this.subscribeToReadMessage();
      });
    }

  }

  subscribeToTopic() {
    if (this.stompClient) {
      this.stompClient.subscribe('/topic', (message) => {
        console.log(message);
        const parsedMessage: Message = JSON.parse(message.body);
        this.messageSubject.next(parsedMessage);
      });
    }
  }

  subscribeToReadMessage() {
    if (this.stompClient) {
      this.stompClient.subscribe("/readMessage", (message) => {
        this.readMessageSubject.next(JSON.parse(message.body))
      })
    }
  }


  getMessage(): Subject<Message> {
    return this.messageSubject;
  }

  getReadMessages(): Subject<ReadMessage> {
    return this.readMessageSubject;
  }


  sendMessage(destination: string, message: any): void {
    if (this.stompClient && this.stompClient.connected && this.isConnected) {
      console.log(message);
      this.stompClient.send(destination, {}, JSON.stringify(message));
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