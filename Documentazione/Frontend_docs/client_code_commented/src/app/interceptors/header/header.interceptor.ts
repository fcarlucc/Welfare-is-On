import { HttpInterceptorFn, HttpRequest, HttpHandlerFn, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * Intercettore HTTP per aggiungere intestazioni personalizzate alle richieste.
 * 
 * Questo intercettore aggiunge un'intestazione personalizzata a tutte le richieste HTTP
 * in uscita, utile per includere informazioni aggiuntive come token di autenticazione
 * o altri dati necessari.
 * 
 * @param {HttpRequest<unknown>} req - La richiesta HTTP in ingresso.
 * @param {HttpHandlerFn} next - La funzione per passare la richiesta alla prossima fase della catena.
 * @return {Observable<HttpEvent<unknown>>} - Un observable con l'evento HTTP.
 */
export const headerInterceptor: HttpInterceptorFn = (
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
): Observable<HttpEvent<unknown>> => {
  // Clona la richiesta originale e aggiunge intestazioni personalizzate
  const clonedRequest = req.clone({
    setHeaders: {
      'X-Custom-Header': 'YourCustomHeaderValue'
    }
  });

  // Passa la richiesta clonata alla prossima fase della catena di intercettori
  return next(clonedRequest);
};
