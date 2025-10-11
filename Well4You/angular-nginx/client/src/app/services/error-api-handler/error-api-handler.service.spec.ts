import { TestBed } from '@angular/core/testing';

import { ErrorApiHandlerService } from './error-api-handler.service';

describe('ErrorApiHandlerService', () => {
  let service: ErrorApiHandlerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ErrorApiHandlerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
