import { AfterViewInit, Component, OnDestroy, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Status, User } from '../../model/user';
import { Router, RouterLink } from '@angular/router';
import { AppService } from '../../service/app.service';
import { WebsocketService } from '../../service/websocket.service';
import { State } from '../../model/conversation';
import { EventService } from '../../service/event.service';
import { FindOther } from '../../model/findOther';
import { DatePipe } from '@angular/common';
import { DateService } from '../../service/date.service';
import { INTERVALTIME } from '../../shared/helper/consts';


@Component({
  selector: 'app-side-bar',
  imports: [FormsModule, RouterLink],
  templateUrl: './side-bar.component.html',
  styleUrl: './side-bar.component.css'
})
export class SideBarComponent implements AfterViewInit, OnDestroy {

  dotColor: "green" | "transparent" = "green";
  user = signal<User | null>(null);
  findUsers: FindOther[] = [];
  userText = '';

  timer: any;

  constructor(private appService: AppService, private webSocketService: WebsocketService, private eventService: EventService, private router: Router, private dateService: DateService) { }

  emitRead() {
    this.eventService.emitReadMessages();
  }

  get math() {
    return Math;
  }

  get dateS() {
    return this.dateService;
  }

  createDate(date: string) {
    return new Date(date);
  }

  get date() {
    return new Date;
  }

  forceUpdate() {
    if (this.user()) {
      this.user = this.user
    }

  }

  showChat(id: number) {
    const user = this.appService.getUser();
    if (user) {
      this.appService.getConversationId(user()!.id, id)?.subscribe({
        next: value => {
          this.router.navigate([`/app/${value}`])
        },
        error: err => {
          console.error(err);
        }
      });
    }
    this.findUsers = [];
    this.userText = '';


  }

  searchUsers(event: Event) {
    const target = event.target as HTMLInputElement;

    const value = target?.value?.trim();
    if (value.length < 2) {
      this.findUsers = [];
    }

    if (value.length > 2 && value) {
      console.log(target.value);
      this.appService.findUsers(value)?.subscribe(value => {
        this.findUsers = value
        console.log(this.findUsers);
      });
    }
  }

  setDotColor(status: Status) {
    switch (status) {
      case Status.ONLINE:
        return "green"
      case Status.OFFLINE:
        return "transparent"
    }
  }

  ngAfterViewInit(): void {
    this.user = this.appService.getUser();
    this.webSocketService.getMessage().subscribe(message => {
      this.user.update(user => {
        console.log(message);
        if (user) {
          let conversation = user.conversations.find(c => c.conversationId === message.conversationId)

          if (conversation) {
            conversation.lastMessage = message.content
            conversation.otherUserId === message.senderId ? conversation.isLastMessageSender = false : conversation.isLastMessageSender = true;
            conversation.state = message.state;
            conversation.lastMessageTimestamp = message.sentTime;
            conversation.lastMessageUpdateTimestamp = message.updateTime;
          } else if (message?.senderId === user.id || message?.receiverId === user.id) {
            if (message?.conversationId) {
              this.appService.fetchNewConversation(message.conversationId).subscribe({
                next: value => {
                  console.log(value);
                  user.conversations.push(value);
                },
                error: err => {
                  console.error(err);
                }
              })
            }
          }
        }
        return user;
      })
    })

    this.webSocketService.getStatus().subscribe(value => {
      this.user.update(user => {
        if (user) {
          user.conversations.map(conv => {
            conv.otherUserId === value.id ? conv.status = value.status : conv.status
          })
        } return user;
      })
    })

    this.webSocketService.getReadMessages().subscribe(message => {
      this.user.update(user => {
        if (user) {
          user.conversations.map(conv => {
            if (message.conversationId === conv.conversationId) {
              conv.state = message.state;
              conv.lastMessageUpdateTimestamp = message.updateTime
            }
          })

        }
        return user;
      })
    })

    this.webSocketService.getPhotoUpdate().subscribe(update => {

      if (update.userId === this.user()?.id) {
        this.user.update(user => {
          if (user) {
            user.imageUrl = update.imageUrl
          }
          return user;
        })
      } else {
        const conversation = this.user()?.conversations.find(conv => conv.otherUserId === update.userId)
        if (conversation) {
          conversation.imageUrl = update.imageUrl
        } else {
          return;
        }
      }
    })


    this.timer = setInterval(() => {
      this.forceUpdate();
    }, INTERVALTIME);

    this.webSocketService.getUpdatedUsername().subscribe(value => {
      console.log(value);
      this.user.update(user => {
        if (user != null) {
          if (user.id === value.id) {
            user.username = value.newUsername
          } else {
            const conversation = user.conversations.find(conv => conv.otherUserId === value.id);

            if (conversation) {
              conversation.otherUser = value.newUsername
            }
          }

        }
        return user;
      })
    })
  }

  ngOnDestroy(): void {
    clearInterval(this.timer);
  }

  get State() {
    return State;
  }

  get Status() {
    return Status;
  }



}
