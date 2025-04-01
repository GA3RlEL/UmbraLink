import { Component, inject, OnInit } from '@angular/core';
import { WebsocketService } from '../../service/websocket.service';

@Component({
  selector: 'app-websocket-error',
  imports: [],
  templateUrl: './websocket-error.component.html',
  styleUrl: './websocket-error.component.css'
})
export class WebsocketErrorComponent implements OnInit {
  websocketService = inject(WebsocketService)
  isReconnecing = false;


  ngOnInit(): void {
    this.websocketService.getisReconnecting().subscribe(value => {
      {
        this.isReconnecing = value;
      }
    })
  }

  connectToWebsocket() {
    if (localStorage.getItem('authToken')) {
      this.websocketService.getisReconnecting().next(true);
      this.websocketService.connect({
        "Authorization": localStorage.getItem("authToken"),
        "Reconnecting-Attempt": this.isReconnecing
      });
    }
  }

}
