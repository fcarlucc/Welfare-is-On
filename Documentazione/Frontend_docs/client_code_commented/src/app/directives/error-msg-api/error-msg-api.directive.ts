import { Directive, ElementRef, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';

import { ErrorApiHandlerService } from '../../services/error-api-handler/error-api-handler.service';

/**
 * Direttiva per visualizzare messaggi di errore API direttamente nell'elemento DOM.
 * 
 * Questa direttiva si iscrive a un flusso di messaggi di errore provenienti dal servizio
 * `ErrorApiHandlerService` e aggiorna il testo dell'elemento associato con il messaggio di errore
 * ricevuto.
 * 
 * @export
 * @class ErrorMessageApiDirective
 * @implements {OnInit}
 * @implements {OnDestroy}
 */
@Directive({
  selector: '[appErrorMessageApi]',
  standalone: true
})
export class ErrorMessageApiDirective implements OnInit, OnDestroy {
  /** Sottoscrizione al flusso di errori API */
  private errorSubscription!: Subscription;

  /**
   * Crea un'istanza di ErrorMessageApiDirective.
   * 
   * @param {ElementRef} el - Riferimento all'elemento DOM a cui è applicata la direttiva.
   * @param {ErrorApiHandlerService} errorHandler - Servizio per la gestione degli errori API.
   * @memberof ErrorMessageApiDirective
   */
  constructor(
    private el: ElementRef,
    private errorHandler: ErrorApiHandlerService
  ) {}

  /**
   * Inizializza la direttiva. Si iscrive al flusso di messaggi di errore e aggiorna il testo
   * dell'elemento DOM con il messaggio di errore.
   * 
   * @memberof ErrorMessageApiDirective
   */
  ngOnInit(): void {
    this.errorSubscription = this.errorHandler.error$.subscribe(errorMessage => {
      this.el.nativeElement.innerText = errorMessage;
    });
  }

  /**
   * Pulisce la sottoscrizione al flusso di messaggi di errore quando la direttiva viene distrutta.
   * 
   * @memberof ErrorMessageApiDirective
   */
  ngOnDestroy(): void {
    if (this.errorSubscription) {
      this.errorSubscription.unsubscribe();
    }
  }
}
