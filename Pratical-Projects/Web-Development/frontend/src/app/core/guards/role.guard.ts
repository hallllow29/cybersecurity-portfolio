import { inject } from '@angular/core';
import { CanActivateFn, Router, ActivatedRouteSnapshot } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const roleGuard: CanActivateFn = (route, state) => {

  const auth = inject(AuthService);
  const router = inject(Router);

  const userRole = auth.getRole();
  const expectedRole = route.data?.['role'];

  if (userRole === expectedRole) {
    return true;
  }

  router.navigate(['/login']);
  return false;
};
