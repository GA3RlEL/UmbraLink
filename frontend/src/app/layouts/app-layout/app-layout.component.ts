import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SideBarComponent } from "../../main-app/side-bar/side-bar.component";
import { AppService } from '../../service/app.service';
import { User } from '../../model/user';

@Component({
  selector: 'app-app-layout',
  imports: [RouterModule, SideBarComponent],
  templateUrl: './app-layout.component.html',
  styleUrl: './app-layout.component.css'
})
export class AppLayoutComponent {
  user!: User | undefined;

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
