import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, RouterStateSnapshot } from '@angular/router';

import { AuthService } from '../../services/auth/auth.service';

export const authAdminGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
  ) => {

    const authService = inject(AuthService)

    return authService.isAuthenthicatedAdmin()
};
