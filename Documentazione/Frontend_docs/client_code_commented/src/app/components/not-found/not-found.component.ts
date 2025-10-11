import { Component } from '@angular/core';

/**
 * Componente per la pagina di errore 404 (Pagina non trovata).
 * Mostra un messaggio o una vista quando l'utente naviga verso una rotta non definita.
 * 
 * @export
 * @class NotFoundComponent
 */
@Component({
  selector: 'app-not-found',
  standalone: true,
  imports: [],
  templateUrl: './not-found.component.html',
  styleUrls: ['./not-found.component.scss']
})
export class NotFoundComponent {

  /**
   * Gestisce il clic sul pulsante per tornare alla pagina precedente.
   * Usa la funzione `window.history.back()` per navigare alla pagina precedente e ricarica la pagina dopo un breve ritardo.
   * 
   * @memberof NotFoundComponent
   */
  onClick() {
    console.log('entered');
    window.history.back();
  }

}
