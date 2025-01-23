import { animate, style, transition, trigger } from '@angular/animations';
import { AfterViewChecked, Component, ElementRef, Input, OnDestroy, OnInit, signal, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { WebsocketService } from '../../service/websocket.service';
import { Subscription } from 'rxjs';
import { AppService } from '../../service/app.service';
import { ActivatedRoute } from '@angular/router';
import { Message, MessageToSend } from '../../model/conversation';
import { User } from '../../model/user';
import { Title } from '@angular/platform-browser';





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


export class ConversationComponent implements OnInit, OnDestroy, AfterViewChecked {
  user = signal<User | null>(null)
  message: string = "";
  isLastMessage!: boolean;
  messages: Message[] = [];
  conversationId: number | null = null;
  receiverId: number | null = null;
  @ViewChild("conversationElement", { static: true }) conversationElement!: ElementRef<HTMLDivElement>
  maxLenght: number = 100;


  constructor(private websocket: WebsocketService, private appService: AppService, private activatedRoute: ActivatedRoute, private title: Title) { }
  ngAfterViewChecked(): void {
    this.scrollToBottom();
  }
  ngOnDestroy(): void {
    this.websocket.disconnect();
  }

  scrollToBottom() {
    const scroll = this.conversationElement.nativeElement.scrollHeight

    this.conversationElement.nativeElement.scrollTo({ top: scroll })
  }

  ngOnInit(): void {
    this.websocket.connect();
    this.user = this.appService.getUser();
    this.websocket.getMessage().subscribe(message => {
      this.messages.push(message);
    })

    this.activatedRoute.params.subscribe(param => {
      this.appService.getConversationMessages(param['id'])?.subscribe({
        next: value => {
          console.log(value);
          this.messages = value.messages;
          this.conversationId = value.conversationId;
          this.receiverId = value.receiverId;
          this.title.setTitle("Umbralink | " + value.receiverName)
          console.log(this.messages);
        }, error: error => {
          console.error(error)
        },
      })
    })
  }


  sendMessage() {
    if (this.user !== null && this.user !== undefined && this.user()?.id !== null && this.conversationId !== null && this.receiverId !== null && this.message.length > 0) {
      const message: MessageToSend = {
        content: this.message,
        senderId: this.user()!.id,
        conversationId: this.conversationId,
        receiverId: this.receiverId,
        sentTime: new Date(),
        messageType: "TEXT",
      }
      this.websocket.sendMessage("/app/topic", message);
      console.log(message);
      this.message = '';
    }


  }

  isLastMessageFromSender(messageIndex: number): boolean {
    if (messageIndex === this.messages.length - 1) return true;
    return this.messages[messageIndex].senderId !== this.messages[messageIndex + 1].senderId;
  }


}
