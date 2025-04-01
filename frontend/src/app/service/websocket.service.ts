import { inject, Injectable } from '@angular/core';
import { CompatClient, IFrame, Stomp } from '@stomp/stompjs';
import { Subject } from 'rxjs';
import { Message, ReadMessage } from '../model/conversation';
import { StatusInterface, UpdatePhotoInteface, UpdateUsernameInterface } from '../model/user';
import { WSURL } from '../shared/helper/consts';
import { AppService } from './app.service';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private appService = inject(AppService)
  private stompClient: CompatClient | null = null;
  private isConnected = false;
  private isReconnecting: Subject<boolean> = new Subject<boolean>
  private reFetchMessages: Subject<any> = new Subject<any>
  private messageSubject: Subject<Message> = new Subject<Message>();
  private readMessageSubject: Subject<ReadMessage> = new Subject<ReadMessage>();
  private statusSubject: Subject<StatusInterface> = new Subject<StatusInterface>();
  private updatePhotoSubject: Subject<UpdatePhotoInteface> = new Subject<UpdatePhotoInteface>
  private updateUsernameSubject: Subject<UpdateUsernameInterface> = new Subject<UpdateUsernameInterface>
  constructor() {
  }

  connect(headers: any): void {
    if (!this.stompClient?.connected) {
      this.stompClient = Stomp.client(WSURL);

      this.stompClient.heartbeatIncoming = 1000;
      this.stompClient.heartbeatOutgoing = 1000;

      this.stompClient.onWebSocketClose = (event: CloseEvent) => {
        this.isConnected = false;
        this.isReconnecting.next(false);
      }

      this.stompClient.onStompError = (frame: IFrame) => {
        this.isConnected = false;
      }


      this.stompClient.debug = () => { };
      this.stompClient.connect(headers, () => {
        this.isConnected = true
        this.isReconnecting.next(false);
        this.subscribeToTopic();
        this.subscribeToReadMessage();
        this.subscribeToStatus();
        this.subscribeToUpdatePhoto();
        this.subscribeToUpdateUsername();

        if (headers['Reconnecting-Attempt'] && headers['Reconnecting-Attempt'] === true) {
          this.appService.getUserDetails()?.subscribe({
            next: (user) => {
              this.appService.setUser(user);
            },
            error: (err) => {
              console.error(err);
            }

          });
          this.reFetchMessages.next(null);
        }

      });
    }

  }


  subscribeToTopic() {
    if (this.stompClient) {
      this.stompClient.subscribe('/topic', (message) => {
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

  subscribeToUpdateUsername() {
    if (this.stompClient) {
      this.stompClient.subscribe("/updateUsername", (status) => {
        const parsedValue = JSON.parse(status.body);
        this.updateUsernameSubject.next(parsedValue);
      })
    }
  }

  getUpdatedUsername(): Subject<UpdateUsernameInterface> {
    return this.updateUsernameSubject;
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

  getisReconnecting(): Subject<boolean> {
    return this.isReconnecting;
  }

  getIsConnected() {
    return this.isConnected;
  }

  getReFetchMessages(): Subject<any> {
    return this.reFetchMessages;
  }



  sendMessage(destination: string, message: any): void {
    if (this.stompClient && this.stompClient.connected && this.isConnected) {
      this.stompClient.send(destination, {}, JSON.stringify(message));
    }
  }


  disconnect(): void {
    if (this.stompClient) {
      this.stompClient.disconnect(() => {
        this.isConnected = false;
      })
    }
  }

  getStompClient() {
    return this.stompClient;
  }
}