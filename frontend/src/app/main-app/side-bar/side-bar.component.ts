import { Component, Input, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Conversation, User } from '../../model/user';

@Component({
  selector: 'app-side-bar',
  imports: [FormsModule],
  templateUrl: './side-bar.component.html',
  styleUrl: './side-bar.component.css'
})
export class SideBarComponent {
  dotColor: "red" | "yellow" | "green" | "transparent" = "green";
  @Input({ required: true }) user: User | undefined;


}
