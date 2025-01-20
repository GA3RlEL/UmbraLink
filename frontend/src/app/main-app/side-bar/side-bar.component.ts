import { Component, Input, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Conversation, User } from '../../model/user';
import { RouterLink } from '@angular/router';
import { AppService } from '../../service/app.service';

@Component({
  selector: 'app-side-bar',
  imports: [FormsModule, RouterLink],
  templateUrl: './side-bar.component.html',
  styleUrl: './side-bar.component.css'
})
export class SideBarComponent {

  dotColor: "red" | "yellow" | "green" | "transparent" = "green";
  @Input({ required: true }) user: User | undefined;



}
