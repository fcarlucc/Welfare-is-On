import { Component, NO_ERRORS_SCHEMA } from '@angular/core';
import { PreHeaderComponent } from '../pre-header.component';

/**
 * Componente per la gestione delle sezioni all'interno della pagina.
 * Include funzionalità per la navigazione e per il controllo dello stato di autenticazione.
 * 
 * @export
 * @class SectionsComponent
 */
@Component({
  selector: 'app-sections',
  standalone: true,
  imports: [PreHeaderComponent],
  schemas: [NO_ERRORS_SCHEMA],
  templateUrl: './sections.component.html',
  styleUrls: ['./sections.component.scss']
})
export class SectionsComponent {

  /**
   * Crea un'istanza di SectionsComponent.
   * 
   * @memberof SectionsComponent
   */
  constructor() {}

  /**
   * Torna alla pagina precedente nella cronologia del browser e ricarica la pagina.
   * 
   * @memberof SectionsComponent
   */
  goBack() {
    window.history.back();
    setTimeout(() => {
      window.location.reload();
    }, 100);
  }

  /**
   * Verifica se l'utente è in una delle pagine di autenticazione.
   * 
   * @returns {boolean} - Indica se l'utente è in una delle pagine di autenticazione.
   * @memberof SectionsComponent
   */
  isSigning(): boolean {
    console.log('current path:', window.location.pathname);
    return [
      '/sign-in',
      '/sign-in/2FA',
      '/forgot-password',
      '/reset-password',
      '/sign-up',
      '/sign-up/survey',
      '/sign-up/2FA'
    ].includes(window.location.pathname);
  }
}
