import { Component } from '@angular/core';
import { HeroSectionComponent } from "./hero-section/hero-section.component";

@Component({
  selector: 'app-landing-page',
  imports: [HeroSectionComponent],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.css'
})
export class LandingPageComponent {

}
