import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);

  return next(req).pipe(catchError((err) => {

    if (req.url.includes('/token/reset/password')) {
      return throwError(() => err)
    }

    if (req.url.includes('/token/validate')) {
      router.navigate(['/'])
      return throwError(() => err)
    }

    if (req.url.includes('/user/restore/password')) {
      return throwError(() => err)
    }

    if (err.error.errorCode === 403 || err.error.errorCode === 500 || err.error.errorCode === 401 || err.error.errorCode === 404
    ) {
      router.navigate(['/app/error'], {
        state: {
          errorCode: err.error.errorCode || 500,
          errorMessage: err.error.message || "An error occured"
        }
      })
    }
    return throwError(() => err);
  }));
};
