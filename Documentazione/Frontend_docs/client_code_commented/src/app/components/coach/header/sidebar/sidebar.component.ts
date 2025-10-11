import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RedirectService } from '../../../../services/redirect/redirect.service';

/**
 * Componente per la barra laterale del coach.
 * Gestisce la visibilità delle sezioni e la navigazione tra le rotte.
 * @export
 * @class SidebarComponent
 */
@Component({
  selector: 'app-sidebar-coach',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {
  /**
   * Flag per controllare la visibilità della sezione.
   * @type {boolean}
   * @memberof SidebarComponent
   */
  sectionOn: boolean = false;

  /**
   * Flag per controllare la visibilità del pilastro (un'ulteriore sezione della sidebar).
   * @type {boolean}
   * @memberof SidebarComponent
   */
  pillarOn: boolean = false;

  /**
   * Crea un'istanza di SidebarComponent.
   * @param {RedirectService} redirectService - Servizio per la gestione delle navigazioni.
   * @memberof SidebarComponent
   */
  constructor(
    private redirectService: RedirectService
  ) {}

  /**
   * Alterna la visibilità della sezione e del pilastro della barra laterale.
   * Impedisce la propagazione dell'evento di clic per evitare conflitti con altri eventi.
   * @param {Event} event - L'evento di clic che ha attivato la funzione.
   * @memberof SidebarComponent
   */
  toggleSection(event: Event) {
    event.stopPropagation(); // Impedisce la propagazione dell'evento di clic
    this.sectionOn = !this.sectionOn; // Alterna la visibilità della sezione
    this.pillarOn = !this.pillarOn; // Alterna la visibilità del pilastro
  }

  /**
   * Gestisce la navigazione verso una rotta specificata.
   * Utilizza il servizio di reindirizzamento per impostare la rotta.
   * @param {string} route - La rotta verso cui navigare.
   * @memberof SidebarComponent
   */
  navigateTo(route: string): void {
    this.redirectService.setRedirect(route);
  }
}
