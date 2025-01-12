import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppService } from '../../service/app.service';
import { user } from '../../model/user';

@Component({
  selector: 'app-side-bar',
  imports: [FormsModule],
  templateUrl: './side-bar.component.html',
  styleUrl: './side-bar.component.css'
})
export class SideBarComponent implements OnInit {
  dotColor: "red" | "yellow" | "green" | "transparent" = "green";
  user!: user | undefined;

  constructor(private appService: AppService) { }
  ngOnInit(): void {
    this.appService.getUserDetails()?.subscribe({
      next: (value) => {
        this.user = value;
        console.log(this.user);
      },
      error: (err) => {
        console.error(err);
      }
    });
  }



}
