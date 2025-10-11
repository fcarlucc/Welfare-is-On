import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

/**
 * Servizio per la gestione degli errori API.
 * Questo servizio permette di emettere messaggi di errore e di osservarli attraverso uno stream.
 */
@Injectable({
  providedIn: 'root'
})
export class ErrorApiHandlerService {

  /**
   * Oggetto Subject utilizzato per emettere messaggi di errore.
   * @private
   */
  private errorSubject = new Subject<string>();

  /**
   * Observable che emette i messaggi di errore.
   * Gli osservatori possono iscriversi a questo stream per ricevere aggiornamenti sugli errori.
   */
  error$ = this.errorSubject.asObservable();

  /**
   * Gestisce un messaggio di errore emettendolo attraverso lo stream degli errori.
   * Questo metodo viene utilizzato per notificare agli osservatori che si è verificato un errore.
   * @param errorMessage - Il messaggio di errore da emettere.
   */
  handleError(errorMessage: string): void {
    this.errorSubject.next(errorMessage);
  }

}
