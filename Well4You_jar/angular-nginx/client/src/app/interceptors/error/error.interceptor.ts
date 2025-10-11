import { HttpInterceptorFn, HttpRequest, HttpHandlerFn, HttpEvent } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { inject } from '@angular/core';
import { ErrorApiHandlerService } from '../../services/error-api-handler/error-api-handler.service';
import { AuthService } from '../../services/auth/auth.service';

export const errorInterceptor: HttpInterceptorFn = (
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
): Observable<HttpEvent<unknown>> => {
  const errorHandler = inject(ErrorApiHandlerService)

  const authService = inject(AuthService);

  return next(req).pipe(
    catchError(error => {
      if (error.status === 401) {
        authService.signOut()
      }
      console.error('HTTP Error occurred:', error.message)
      console.log('messaggio api',error.error.error)
      let errorMessage = 'Unknown error occurred'
      if (error.error && error.error.error) {
        errorMessage = error.error.error;
      }
      errorHandler.handleError(errorMessage)
      return throwError(() => new Error(errorMessage))
    })
  );
};
