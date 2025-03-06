import { Component } from '@angular/core';
import { NavBarComponent } from "../../shared/nav-bar/nav-bar.component";
import { RouterModule } from '@angular/router';
import { ErrorComponent } from "../../shared/error/error.component";

@Component({
  selector: 'app-landing-layout',
  imports: [NavBarComponent, RouterModule, ErrorComponent],
  templateUrl: './landing-layout.component.html',
  styleUrl: './landing-layout.component.css'
})
export class LandingLayoutComponent {

}
