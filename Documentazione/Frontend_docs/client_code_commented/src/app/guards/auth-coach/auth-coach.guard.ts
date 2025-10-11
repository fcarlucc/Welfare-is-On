import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, RouterStateSnapshot } from '@angular/router';

import { AuthService } from '../../services/auth/auth.service';

/**
 * Guardiano di route per la protezione delle rotte basata sui privilegi di coach.
 * 
 * Questo guardiano verifica se l'utente è autenticato come coach prima di permettere l'accesso
 * alla rotta richiesta. Utilizza il servizio di autenticazione per determinare
 * i privilegi dell'utente.
 * 
 * @param {ActivatedRouteSnapshot} route - La snapshot della rotta attivata.
 * @param {RouterStateSnapshot} state - La snapshot dello stato del router.
 * @return {boolean} - `true` se l'utente è un coach autenticato, altrimenti `false`.
 */
export const authCoachGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
): boolean => {

  // Inietta il servizio di autenticazione
  const authService = inject(AuthService);

  // Verifica se l'utente è un coach autenticato
  return authService.isAuthenthicatedCoach();
};
