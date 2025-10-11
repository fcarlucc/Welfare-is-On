import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorApiHandlerService {

  private errorSubject = new Subject<string>();
  error$ = this.errorSubject.asObservable();

  handleError(errorMessage: string): void {
    this.errorSubject.next(errorMessage);
  }

}
