import { HttpInterceptorFn, HttpRequest, HttpHandlerFn, HttpEvent } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
): Observable<HttpEvent<unknown>> => {
  const authToken: string | null = sessionStorage.getItem('access_token')

  if (authToken) {
    req = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${authToken}`)
    });
  }
  //  else {
  //   return throwError(() => new Error('Authorization token is missing'));
  // }

  return next(req).pipe(
    catchError(error => {
      return throwError(() => new Error(error));
    })
  );
};
