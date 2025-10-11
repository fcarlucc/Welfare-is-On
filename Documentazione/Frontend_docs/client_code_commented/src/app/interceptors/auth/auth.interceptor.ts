import { HttpInterceptorFn, HttpRequest, HttpHandlerFn, HttpEvent } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

/**
 * Intercettore HTTP per aggiungere un token di autenticazione alle richieste.
 * 
 * Questo intercettore aggiunge un token di accesso alle intestazioni delle richieste HTTP
 * se il token è presente in sessionStorage. Gestisce anche gli errori durante il
 * processamento delle richieste.
 * 
 * @param {HttpRequest<unknown>} req - La richiesta HTTP in ingresso.
 * @param {HttpHandlerFn} next - La funzione per passare la richiesta alla prossima fase della catena.
 * @return {Observable<HttpEvent<unknown>>} - Un observable con l'evento HTTP.
 */
export const authInterceptor: HttpInterceptorFn = (
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
): Observable<HttpEvent<unknown>> => {
  // Ottieni il token di accesso dal sessionStorage
  const authToken: string | null = sessionStorage.getItem('access_token');

  // Se il token è presente, cloniamo la richiesta e aggiungiamo l'intestazione Authorization
  if (authToken) {
    req = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${authToken}`)
    });
  } else {
    // Gestisci il caso in cui il token di accesso non è presente (se necessario)
    // Questa parte è attualmente commentata, ma potrebbe essere riattivata se necessario.
    // return throwError(() => new Error('Authorization token is missing'));
  }

  // Passa la richiesta alla prossima fase e gestisci eventuali errori
  return next(req).pipe(
    catchError(error => {
      // Propaga l'errore, includendo informazioni aggiuntive se necessario
      return throwError(() => new Error(error.message || 'Unknown error'));
    })
  );
};
