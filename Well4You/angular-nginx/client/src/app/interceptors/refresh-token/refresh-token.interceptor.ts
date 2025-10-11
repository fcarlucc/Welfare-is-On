import { HttpInterceptorFn } from '@angular/common/http';
import { HttpEvent, HttpHandlerFn, HttpRequest, HttpResponse } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

export const refreshTokenInterceptor: HttpInterceptorFn = (
  req: HttpRequest<any>,
  next: HttpHandlerFn
): Observable<HttpEvent<any>> => {
  console.log('Entered refresh interceptor');

  return next(req).pipe(
    tap(event => {
      // console.log('Verifying the presence of the authorization header');

      if (event instanceof HttpResponse) { 
        const token = event.headers.get('Authorization');
        if (token && token.startsWith('Bearer ')) {
          sessionStorage.setItem('access_token', token.split(' ')[1]);
        }
      }
    })
  );
};
