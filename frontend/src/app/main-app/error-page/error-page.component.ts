import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-error-page',
  imports: [],
  templateUrl: './error-page.component.html',
  styleUrl: './error-page.component.css'
})
export class ErrorPageComponent {
  errorCode: string = '';
  errorMessage: string = '';

  constructor(private router: Router) {
    this.errorCode = router.getCurrentNavigation()?.extras.state?.['errorCode'] || 500;
    this.errorMessage = router.getCurrentNavigation()?.extras.state?.['errorMessage'] || "Unknown Error"
  }

  navigateToHome() {
    this.router.navigate(['/app'])
  }

}
