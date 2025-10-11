import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, RouterStateSnapshot } from '@angular/router';

import { AuthService } from '../../services/auth/auth.service';

/**
 * Guardiano di route per la protezione delle rotte basata sull'autenticazione.
 * 
 * Questo guardiano verifica se l'utente è autenticato prima di permettere l'accesso
 * alla rotta richiesta. Utilizza il servizio di autenticazione per determinare
 * lo stato di autenticazione dell'utente.
 * 
 * @param {ActivatedRouteSnapshot} route - La snapshot della rotta attivata.
 * @param {RouterStateSnapshot} state - La snapshot dello stato del router.
 * @return {boolean} - `true` se l'utente è autenticato, altrimenti `false`.
 */
export const authGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
): boolean => {

  // Inietta il servizio di autenticazione
  const authService = inject(AuthService);

  // Verifica se l'utente è autenticato
  return authService.isAuthenthicatedUser();
};
