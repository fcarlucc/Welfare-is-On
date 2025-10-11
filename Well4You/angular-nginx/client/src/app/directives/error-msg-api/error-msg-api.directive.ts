import { Directive, ElementRef, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';

import { ErrorApiHandlerService } from '../../services/error-api-handler/error-api-handler.service';

@Directive({
  selector: '[appErrorMessageApi]',
  standalone: true
})
export class ErrorMessageApiDirective implements OnInit, OnDestroy {
  private errorSubscription!: Subscription;

  constructor(
    private el: ElementRef,
    private errorHandler: ErrorApiHandlerService
  ) {}

  ngOnInit(): void {
    this.errorSubscription = this.errorHandler.error$.subscribe(errorMessage => {
      this.el.nativeElement.innerText = errorMessage;
    });
  }

  ngOnDestroy(): void {
    if (this.errorSubscription) {
      this.errorSubscription.unsubscribe();
    }
  }
}
