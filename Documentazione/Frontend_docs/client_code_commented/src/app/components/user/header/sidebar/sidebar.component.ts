import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RedirectService } from '../../../../services/redirect/redirect.service';

/**
 * Componente per la barra laterale dell'applicazione.
 * Gestisce la visibilità delle sezioni e la navigazione verso rotte specificate.
 * 
 * @export
 * @class SidebarComponent
 */
@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {
  /**
   * Indica se la sezione della barra laterale è attualmente visibile.
   * 
   * @type {boolean}
   * @memberof SidebarComponent
   */
  sectionOn: boolean = false;

  /**
   * Indica se il pilastro della barra laterale è attualmente visibile.
   * 
   * @type {boolean}
   * @memberof SidebarComponent
   */
  pillarOn: boolean = false;

  /**
   * Crea un'istanza di SidebarComponent.
   * 
   * @param {RedirectService} redirectService - Servizio per la gestione della navigazione.
   * @memberof SidebarComponent
   */
  constructor(
    private redirectService: RedirectService
  ) {}

  /**
   * Alterna la visibilità della sezione e del pilastro nella barra laterale.
   * 
   * @param {Event} event - L'evento di clic.
   * @memberof SidebarComponent
   */
  toggleSection(event: Event) {
    event.stopPropagation();
    this.sectionOn = !this.sectionOn;
    this.pillarOn = !this.pillarOn;
  }

  /**
   * Gestisce la navigazione verso una rotta specificata.
   * 
   * @param {string} route - La rotta verso cui navigare.
   * @memberof SidebarComponent
   */
  navigateTo(route: string): void {
    this.redirectService.setRedirect(route);
  }
}
