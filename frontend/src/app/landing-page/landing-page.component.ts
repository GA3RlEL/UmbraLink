import { Component } from '@angular/core';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { HeroSectionComponent } from "./hero-section/hero-section.component";

@Component({
  selector: 'app-landing-page',
  imports: [NavBarComponent, HeroSectionComponent],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.css'
})
export class LandingPageComponent {

}
