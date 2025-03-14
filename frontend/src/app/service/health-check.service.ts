import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, map, of } from 'rxjs';
import { ErrorService } from './error.service';
import { v4 as uuidv4 } from 'uuid'
import { Router } from '@angular/router';
import { BASEURL } from '../shared/helper/consts';

@Injectable({
  providedIn: 'root'
})
export class HealthCheckService {

  private http = inject(HttpClient)
  private errorService = inject(ErrorService)
  private router = inject(Router)
  constructor() { }


  checkHealth() {
    return this.http.get(BASEURL + "/health/ping").pipe(map(() => true),
      catchError(() => {
        this.errorService.addError({
          id: uuidv4(),
          errorCode: 500,
          errorMessage: "Internal Server Error - No connection"
        });
        this.router.navigate(['/']);
        return of(false);
      }))
  }
}
