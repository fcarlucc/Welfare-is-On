import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { authCoachGuard } from './auth-coach.guard';

describe('authCoachGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => authCoachGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
