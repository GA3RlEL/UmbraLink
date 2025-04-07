import { animate, style, transition, trigger } from '@angular/animations';
import { AfterViewChecked, Component, ElementRef, OnDestroy, OnInit, signal, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { WebsocketService } from '../../service/websocket.service';
import { AppService } from '../../service/app.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Message, MessageToSend, ReadMessage, State } from '../../model/conversation';
import { User } from '../../model/user';
import { Title } from '@angular/platform-browser';
import { EventService } from '../../service/event.service';
import { DateService } from '../../service/date.service';
import { INTERVALTIME } from '../../shared/helper/consts';
import { ThrobberComponent } from "../../shared/throbber/throbber.component";
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-conversation',
  imports: [FormsModule, ThrobberComponent],
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


export class ConversationComponent implements OnInit, AfterViewChecked, OnDestroy {
  user = signal<User | null>(null)
  message: string = "";
  isLastMessage!: boolean;
  messages: Message[] = [];
  conversationId: number | null = null;
  receiverName: String | null = null;
  receiverId: number | null = null;
  @ViewChild("conversationElement", { static: true }) conversationElement!: ElementRef<HTMLDivElement>
  @ViewChild("uploadPhotoElement", { static: true }) uploadPhotoElement!: ElementRef<HTMLInputElement>
  maxLenght: number = 100;
  conversationUserPhotoUrl: string | null = null;

  sendPhoto: string | null = null;
  sendFile: Blob | null = null;
  isSendingPhoto = false;

  timer: any;

  private getMessageSubscribtion!: Subscription
  private getReFetchMessagesSubscribtion!: Subscription
  private getReadMessagesSubscribtion!: Subscription
  private checkIfUnreadMessagesSubscribtion!: Subscription
  private getUpdatedUsernameSubscribtion!: Subscription
  private updatePhotoSubscribtion!: Subscription;


  constructor(private websocket: WebsocketService, private appService: AppService, private activatedRoute: ActivatedRoute, private title: Title, private eventService: EventService, private dateService: DateService, private router: Router) { }

  get dateS() {
    return this.dateService;
  }


  ngAfterViewChecked(): void {
    this.scrollToBottom();
  }
  scrollToBottom() {
    const scroll = this.conversationElement.nativeElement.scrollHeight
    this.conversationElement.nativeElement.scrollTo({ top: scroll })
  }

  forceUpdate() {
    if (this.messages) {
      this.messages = [...this.messages];
    }
  }

  clearPhoto() {
    this.sendPhoto = null;
    this.sendFile = null;
    this.uploadPhotoElement.nativeElement.value = ''
  }

  selectPhoto(event: Event) {
    const input = event.target as HTMLInputElement


    if (input.files && input.files.length > 0) {
      this.sendFile = input.files[0]
      const reader = new FileReader();

      reader.onload = (e: any) => {
        this.sendPhoto = e.target.result;
      }

      reader.readAsDataURL(this.sendFile)
    }
  }

  ngOnInit(): void {
    this.user = this.appService.getUser();
    this.getMessageSubscribtion = this.websocket.getMessage().subscribe(message => {
      const conversationId = +this.activatedRoute.snapshot.paramMap.get('id')!;
      if (conversationId === message.conversationId) {
        this.messages.push(message);
      }

    })

    this.fetchMessages();

    this.getReFetchMessagesSubscribtion
      = this.websocket.getReFetchMessages().subscribe(() => {
        this.fetchMessages();
      })

    this.getReadMessagesSubscribtion = this.websocket.getReadMessages().subscribe(message => {
      this.changeMessageToRead(message);
    })

    this.checkIfUnreadMessagesSubscribtion = this.eventService.read$.subscribe(e => {
      this.checkIfUneadMessages(this.messages);
    })

    this.timer = setInterval(() => {
      this.forceUpdate();
    }, INTERVALTIME);

    this.getUpdatedUsernameSubscribtion = this.websocket.getUpdatedUsername().subscribe(value => {
      if (this.receiverId === value.id) {
        this.receiverName = value.newUsername
      }
    })

    this.updatePhotoSubscribtion = this.websocket.getPhotoUpdate().subscribe(update => {
      if (update.userId === this.receiverId) {
        this.conversationUserPhotoUrl = update.imageUrl;
      }
    })

  }

  fetchMessages() {
    this.activatedRoute.params.subscribe(param => {
      this.appService.getConversationMessages(param['id'])?.subscribe({
        next: value => {
          this.messages = value.messages;
          this.conversationUserPhotoUrl = value.photoUrl;
          this.checkIfUneadMessages(this.messages);
          this.conversationId = value.conversationId;
          this.receiverId = value.receiverId;
          this.title.setTitle("Umbralink | " + value.receiverName)
          this.receiverName = value.receiverName
        }, error: error => {
          console.error(error)
        },
      })
    })
  }



  ngOnDestroy(): void {
    this.getMessageSubscribtion.unsubscribe();
    this.getReFetchMessagesSubscribtion.unsubscribe();
    this.getReadMessagesSubscribtion.unsubscribe();
    this.checkIfUnreadMessagesSubscribtion.unsubscribe();
    this.getUpdatedUsernameSubscribtion.unsubscribe();
    this.updatePhotoSubscribtion.unsubscribe();
    clearInterval(this.timer);
  }

  back() {
    this.router.navigate(['/app'])
  }

  checkIfUneadMessages(messages: Message[]) {
    const yourMessages = messages.filter(mess => mess.senderId !== this.user()?.id)

    if (yourMessages.length === 0) return;

    const unSeenMessages = yourMessages.filter(mess => mess.state !== State.SEEN);
    unSeenMessages.map(message => {
      let readPayload: ReadMessage = {
        messageId: message.messageId,
        conversationId: message.conversationId,
        state: message.state,
        updateTime: ''
      }
      this.websocket.sendMessage("/app/readMessage", readPayload)
    })
  }


  changeMessageToRead(message: ReadMessage) {
    let foundIndex = this.messages.findIndex(mess => mess.messageId === message.messageId)
    if (foundIndex !== -1) {
      this.messages[foundIndex].state = message.state;
      this.messages[foundIndex].updateTime = message.updateTime;
      this.forceUpdate();
    }
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
      this.message = '';
    }
  }

  sendImage() {
    if (this.user !== null && this.user !== undefined && this.user()?.id !== null && this.conversationId !== null && this.receiverId !== null && this.sendFile != null && this.sendPhoto != null) {
      this.isSendingPhoto = true;
      const formData = new FormData();
      formData.append('file', this.sendFile)
      const message: MessageToSend = {
        content: this.message,
        senderId: this.user()!.id,
        conversationId: this.conversationId,
        receiverId: this.receiverId,
        sentTime: new Date(),
        messageType: "PHOTO",
      }
      formData.append('data', JSON.stringify(message));
      this.appService.sendPhotoMessage(formData)?.subscribe({
        complete: () => {
          this.clearPhoto();
          this.scrollToBottom();
          this.isSendingPhoto = false;
        }
      });


    }
  }

  isLastMessageFromSender(messageIndex: number): boolean {
    if (messageIndex === this.messages.length - 1) return true;
    return this.messages[messageIndex].senderId !== this.messages[messageIndex + 1].senderId;
  }


}

