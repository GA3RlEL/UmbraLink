import { AfterViewInit, Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { User } from '../../model/user';
import { RouterLink } from '@angular/router';
import { AppService } from '../../service/app.service';
import { WebsocketService } from '../../service/websocket.service';
import { State } from '../../model/conversation';

@Component({
  selector: 'app-side-bar',
  imports: [FormsModule, RouterLink],
  templateUrl: './side-bar.component.html',
  styleUrl: './side-bar.component.css'
})
export class SideBarComponent implements AfterViewInit {

  dotColor: "red" | "yellow" | "green" | "transparent" = "green";
  user = signal<User | null>(null);


  constructor(private appService: AppService, private webSocketService: WebsocketService) { }
  ngAfterViewInit(): void {
    this.user = this.appService.getUser();
    this.webSocketService.connect();
    this.webSocketService.getMessage().subscribe(message => {

      this.user.update(user => {
        if (user) {
          user?.conversations.map(conv => {
            if (conv.conversationId === message.conversationId) {

              conv.lastMessage = message.content
              conv.otherUserId === message.senderId ? conv.isLastMessageSender = false : conv.isLastMessageSender = true;
            }
          })
        }
        return user;
      })
    })
  }

  get State() {
    return State;
  }

  ngOnInit(): void {

  }


}
