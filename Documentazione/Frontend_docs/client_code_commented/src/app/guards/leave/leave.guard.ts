import { CanDeactivateFn, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { SignUpSurveyComponent } from '../../components/auth/sign-up-survey/sign-up-survey.component';

/**
 * Guardiano per la disattivazione della componente SignUpSurveyComponent.
 * 
 * Questo guardiano verifica se è sicuro lasciare la componente `SignUpSurveyComponent`.
 * La funzione `canDeactivate()` della componente viene chiamata per determinare
 * se la navigazione può essere effettuata o se è necessario avvisare l'utente.
 * 
 * @param {SignUpSurveyComponent} component - L'istanza della componente che sta per essere disattivata.
 * @param {ActivatedRouteSnapshot} currentRoute - La snapshot della rotta corrente.
 * @param {RouterStateSnapshot} currentState - La snapshot dello stato del router corrente.
 * @param {RouterStateSnapshot} nextState - La snapshot dello stato del router al quale si sta tentando di navigare.
 * @return {boolean | Promise<boolean> | Observable<boolean>} - Restituisce `true` se è sicuro lasciare la componente, `false` altrimenti. 
 */
export const leaveGuard: CanDeactivateFn<SignUpSurveyComponent> = (
  component: SignUpSurveyComponent,
  currentRoute: ActivatedRouteSnapshot,
  currentState: RouterStateSnapshot,
  nextState: RouterStateSnapshot
) => {
  console.log('leaveGuard chiamata');
  return component.canDeactivate();
};
