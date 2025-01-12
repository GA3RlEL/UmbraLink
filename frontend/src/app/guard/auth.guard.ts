import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

export const authGuard: CanActivateFn = (route, state) => {
  const token = localStorage.getItem("authToken");
  const router = inject(Router);

  if (token) {
    const decode = jwtDecode(token);
    if (decode.exp) {
      if (Date.now() > decode.exp * 1000) {
        localStorage.removeItem('authToken')
        router.navigate(['/login']);
        return false;
      } else {
        return true;
      }
    }
    return true;
  } else {
    router.navigate(['/login']);
    return false;
  }



};
