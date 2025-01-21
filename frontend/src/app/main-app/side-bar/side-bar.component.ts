import { AfterViewInit, Component, Input, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Conversation, User } from '../../model/user';
import { RouterLink } from '@angular/router';
import { AppService } from '../../service/app.service';
import { appConfig } from '../../app.config';

@Component({
  selector: 'app-side-bar',
  imports: [FormsModule, RouterLink],
  templateUrl: './side-bar.component.html',
  styleUrl: './side-bar.component.css'
})
export class SideBarComponent implements AfterViewInit {

  dotColor: "red" | "yellow" | "green" | "transparent" = "green";
  user = signal<User | null>(null);

  constructor(private appService: AppService) { }
  ngAfterViewInit(): void {
    this.user = this.appService.getUser();
  }

  ngOnInit(): void {

  }


}
