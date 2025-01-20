import { animate, style, transition, trigger } from '@angular/animations';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { WebsocketService } from '../../service/websocket.service';
import { Subscription } from 'rxjs';
import { AppService } from '../../service/app.service';
import { ActivatedRoute } from '@angular/router';
import { Conversation, Message } from '../../model/conversation';





@Component({
  selector: 'app-conversation',
  imports: [FormsModule],
  templateUrl: './conversation.component.html',
  styleUrl: './conversation.component.css',
  animations: [
    trigger("sendButton", [
      transition(":enter", [
        style({ opacity: 0 }),
        animate("0.3s ease-in", style({ opacity: 1 }))
      ]),
      transition(":leave", [
        style({ opacity: 1 }),
        animate("0.3s ease-out", style({ opacity: 0 }))
      ])
    ]
    )
  ]
})


export class ConversationComponent implements OnInit, OnDestroy {
  private wsSub!: Subscription;

  message: string = "";
  user_id: number = 1;
  isLastMessage!: boolean;
  messages: Message[] = [];


  constructor(private websocket: WebsocketService, private appService: AppService, private activatedRoute: ActivatedRoute) { }
  ngOnDestroy(): void {
    this.websocket.disconnect();
  }

  ngOnInit(): void {
    this.websocket.connect();

    this.wsSub = this.websocket.onMessage().subscribe({
      next: (value) => {
        console.log(value);
        this.messages.push(value);
      },
      error: (error) => {
        console.error("Websocket error: " + error);
      }
    })

    this.activatedRoute.params.subscribe(param => {
      this.appService.getConversationMessages(param['id'])?.subscribe({
        next: value => {
          console.log(value);
          this.messages = value.messages;
          console.log(this.messages);
        }, error: error => {
          console.error(error)
        }
      })
    })
  }


  sendMessage() {
    this.websocket.sendMessage(this.message);
    console.log(this.messages);
    this.message = '';
  }

  isLastMessageFromSender(messageIndex: number): boolean {
    if (messageIndex === this.messages.length - 1) return true;
    return this.messages[messageIndex].senderId !== this.messages[messageIndex + 1].senderId;
  }


}
