import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const loginPageGuard: CanActivateFn = (route, state) => {
  const token = localStorage.getItem("authToken");
  const router = inject(Router);
  if (!token) {

    return true;

  } else {
    router.navigate(['/app']);
    return false
  }
};
