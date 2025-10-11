import { HttpInterceptorFn, HttpEvent, HttpHandlerFn, HttpRequest, HttpResponse } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

/**
 * Intercettore HTTP per gestire il refresh del token di autenticazione.
 * 
 * Questo intercettore controlla le risposte HTTP per cercare un token di autenticazione
 * aggiornato nell'intestazione della risposta e lo memorizza nel sessionStorage.
 * 
 * @param {HttpRequest<any>} req - La richiesta HTTP in ingresso.
 * @param {HttpHandlerFn} next - La funzione per passare la richiesta alla prossima fase della catena.
 * @return {Observable<HttpEvent<any>>} - Un observable con l'evento HTTP.
 */
export const refreshTokenInterceptor: HttpInterceptorFn = (
  req: HttpRequest<any>,
  next: HttpHandlerFn
): Observable<HttpEvent<any>> => {
  console.log('Entered refresh interceptor');

  return next(req).pipe(
    tap(event => {
      // Solo se l'evento è una risposta HTTP
      if (event instanceof HttpResponse) {
        // Recupera il valore dell'intestazione 'Authorization'
        const token = event.headers.get('Authorization');
        if (token && token.startsWith('Bearer ')) {
          // Estrai e memorizza il nuovo token di accesso
          sessionStorage.setItem('access_token', token.split(' ')[1]);
        }
      }
    })
  );
};
