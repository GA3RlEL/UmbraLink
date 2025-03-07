import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { ErrorService } from '../service/error.service';
import { v4 as uuidv4 } from 'uuid'
import { HttpClient } from '@angular/common/http';
import { catchError, map, of } from 'rxjs';

export const websocketConnectionGuard: CanActivateFn = (route, state) => {
  const http = inject(HttpClient)
  const router = inject(Router)
  const errorService = inject(ErrorService)

  return http.get("http://localhost:8080/api/v1/health/ping").pipe(map(() => true),
    catchError(() => {
      errorService.addError({
        id: uuidv4(),
        errorCode: 500,
        errorMessage: "Internal Server Error - No connection"
      });
      router.navigate(['/']);
      return of(false);
    }))

};
