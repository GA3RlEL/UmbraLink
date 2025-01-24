import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SideBarComponent } from "../../main-app/side-bar/side-bar.component";
import { AppService } from '../../service/app.service';
import { User } from '../../model/user';
import { WebsocketService } from '../../service/websocket.service';

@Component({
  selector: 'app-app-layout',
  imports: [RouterModule, SideBarComponent],
  templateUrl: './app-layout.component.html',
  styleUrl: './app-layout.component.css'
})
export class AppLayoutComponent implements OnInit {
  constructor(private appService: AppService, private websocket: WebsocketService) { }

  ngOnInit(): void {
    this.appService.getUserDetails()?.subscribe({
      next: (user) => {
        this.appService.setUser(user);
      },
      error: (err) => {
        console.error(err);
      }
    });
  }


}
