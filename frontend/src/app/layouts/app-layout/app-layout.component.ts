import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SideBarComponent } from "../../main-app/side-bar/side-bar.component";

@Component({
  selector: 'app-app-layout',
  imports: [RouterModule, SideBarComponent],
  templateUrl: './app-layout.component.html',
  styleUrl: './app-layout.component.css'
})
export class AppLayoutComponent {

}
