import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanDeactivateFn, RouterStateSnapshot } from '@angular/router';

import { SignUpSurveyComponent } from '../../components/auth/sign-up-survey/sign-up-survey.component';

export const leaveGuard: CanDeactivateFn<SignUpSurveyComponent> = (
  component: SignUpSurveyComponent,
  currentRoute: ActivatedRouteSnapshot,
  currentState: RouterStateSnapshot,
  nextState: RouterStateSnapshot
) => {
  console.log('leaveGuard chiamata');
  return component.canDeactivate();
};
