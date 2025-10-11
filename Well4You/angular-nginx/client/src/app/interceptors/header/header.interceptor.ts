import { HttpInterceptorFn, HttpRequest, HttpHandlerFn, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

export const headerInterceptor: HttpInterceptorFn = (
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
): Observable<HttpEvent<unknown>> => {
  const clonedRequest = req.clone({
    setHeaders: {
      'X-Custom-Header': 'YourCustomHeaderValue'
    }
  });

  return next(clonedRequest);
};
