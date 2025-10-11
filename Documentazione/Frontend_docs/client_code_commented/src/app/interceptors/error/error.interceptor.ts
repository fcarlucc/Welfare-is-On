import { HttpInterceptorFn, HttpRequest, HttpHandlerFn, HttpEvent } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { inject } from '@angular/core';
import { ErrorApiHandlerService } from '../../services/error-api-handler/error-api-handler.service';
import { AuthService } from '../../services/auth/auth.service';

/**
 * Intercettore HTTP per gestire gli errori delle API.
 * 
 * Questo intercettore gestisce gli errori delle richieste HTTP, inclusi errori di autenticazione
 * come il codice di stato 401, e invia un messaggio di errore al servizio di gestione errori.
 * 
 * @param {HttpRequest<unknown>} req - La richiesta HTTP in ingresso.
 * @param {HttpHandlerFn} next - La funzione per passare la richiesta alla prossima fase della catena.
 * @return {Observable<HttpEvent<unknown>>} - Un observable con l'evento HTTP.
 */
export const errorInterceptor: HttpInterceptorFn = (
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
): Observable<HttpEvent<unknown>> => {
  // Ottieni le istanze dei servizi necessari
  const errorHandler = inject(ErrorApiHandlerService);
  const authService = inject(AuthService);

  return next(req).pipe(
    catchError(error => {
      // Gestisci errori specifici
      if (error.status === 401) {
        authService.signOut(); // Disconnetti l'utente se non autorizzato
      }
      
      // Log dell'errore nella console
      console.error('HTTP Error occurred:', error.message);
      console.log('API error message:', error.error?.error);

      // Ottieni un messaggio di errore dettagliato
      let errorMessage = 'Unknown error occurred';
      if (error.error && error.error.error) {
        errorMessage = error.error.error;
      }

      // Passa il messaggio di errore al servizio di gestione errori
      errorHandler.handleError(errorMessage);

      // Propaga l'errore modificato
      return throwError(() => new Error(errorMessage));
    })
  );
};
