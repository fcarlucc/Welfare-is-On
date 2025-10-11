import { Component, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SidebarComponent } from './sidebar/sidebar.component';
import { RedirectService } from '../../../services/redirect/redirect.service';
import { ApiService } from '../../../services/api/api.service';

/**
 * Componente per l'intestazione dell'applicazione.
 * Gestisce la visibilità della barra laterale e del profilo dell'utente,
 * oltre alla gestione della disconnessione dell'utente e alla navigazione.
 * 
 * @export
 * @class HeaderComponent
 */
@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, SidebarComponent],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  /**
   * Indica se la barra laterale è attualmente visibile.
   * 
   * @type {boolean}
   * @memberof HeaderComponent
   */
  sidebarOn: boolean = false;

  /**
   * Indica se il menu del profilo è attualmente visibile.
   * 
   * @type {boolean}
   * @memberof HeaderComponent
   */
  profileOn: boolean = false;

  /**
   * Crea un'istanza di HeaderComponent.
   * 
   * @param {ApiService} apiService - Servizio per le chiamate API.
   * @param {RedirectService} redirectService - Servizio per la gestione della navigazione.
   * @memberof HeaderComponent
   */
  constructor(
    private apiService: ApiService,
    private redirectService: RedirectService
  ) {}

  /**
   * Gestisce i clic al di fuori della barra laterale e del profilo dell'utente.
   * Chiude la barra laterale e il menu del profilo se l'utente fa clic al di fuori di questi elementi.
   * 
   * @param {Event} event - L'evento di clic.
   * @memberof HeaderComponent
   */
  @HostListener('document:click', ['$event'])
  clickOutside(event: Event) {
    if (this.sidebarOn || this.profileOn) {
      const target = event.target as HTMLElement;
      if (!target.closest('.header')) {
        this.sidebarOn = false;
        this.profileOn = false;
      }
    }
  }

  /**
   * Alterna la visibilità della barra laterale.
   * Se il menu del profilo è visibile, viene chiuso.
   * 
   * @memberof HeaderComponent
   */
  toggleMenu() {
    console.log('clicked menu');
    if (this.profileOn) {
      this.profileOn = false;
    }
    this.sidebarOn = !this.sidebarOn;
  }

  /**
   * Alterna la visibilità del menu del profilo.
   * Se la barra laterale è visibile, viene chiusa.
   * 
   * @memberof HeaderComponent
   */
  toggleProfile() {
    console.log('clicked profile');
    if (this.sidebarOn) {
      this.sidebarOn = false;
    }
    this.profileOn = !this.profileOn;
  }

  /**
   * Effettua il logout dell'utente, rimuovendo i dati di sessione e chiamando l'API di logout.
   * 
   * @memberof HeaderComponent
   */
  async signOut() {
    try {
      await this.apiService.post('api/auth/logout', {}).toPromise();
      sessionStorage.removeItem('access_token');
      sessionStorage.removeItem('user_id');
      sessionStorage.removeItem('email');
      sessionStorage.removeItem('full_name');
      sessionStorage.removeItem('role');
    } catch (error) {
      console.error('logout error', error);
    }
  }

  /**
   * Gestisce la navigazione verso una rotta specificata.
   * 
   * @param {string} route - La rotta verso cui navigare.
   * @memberof HeaderComponent
   */
  navigateTo(route: string): void {
    this.redirectService.setRedirect(route);
  }
}
