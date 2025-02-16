import { Injectable } from '@angular/core';
import { CompatClient, Stomp } from '@stomp/stompjs';
import { Subject } from 'rxjs';
import { Message, ReadMessage } from '../model/conversation';
import { StatusInterface, UpdatePhotoInteface } from '../model/user';
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
  private statusSubject: Subject<StatusInterface> = new Subject<StatusInterface>();
  private updatePhotoSubject: Subject<UpdatePhotoInteface> = new Subject<UpdatePhotoInteface>
  constructor() {
  }

  connect(headers: any): void {
    if (!this.stompClient?.connected) {
      this.stompClient = Stomp.client(this.WS_URL);
      this.stompClient.connect(headers, () => {
        this.isConnected = true
        this.subscribeToTopic();
        this.subscribeToReadMessage();
        this.subscribeToStatus();
        this.subscribeToUpdatePhoto();
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

  subscribeToStatus() {
    if (this.stompClient) {
      this.stompClient.subscribe("/status", (status) => {
        const parsedMessage = JSON.parse(status.body);
        this.statusSubject.next(parsedMessage);
      })
    }
  }

  subscribeToUpdatePhoto() {
    if (this.stompClient) {
      this.stompClient.subscribe('/photoUpdate', status => {
        const parsedMessage = JSON.parse(status.body);
        this.updatePhotoSubject.next(parsedMessage);
      })
    }
  }


  getMessage(): Subject<Message> {
    return this.messageSubject;
  }

  getReadMessages(): Subject<ReadMessage> {
    return this.readMessageSubject;
  }

  getStatus(): Subject<StatusInterface> {
    return this.statusSubject;
  }

  getPhotoUpdate(): Subject<UpdatePhotoInteface> {
    return this.updatePhotoSubject;
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