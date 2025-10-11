import { Component } from '@angular/core';

import { SidebarComponent } from './sidebar/sidebar.component';

import { RedirectService } from '../../../services/redirect/redirect.service';
import { ApiService } from '../../../services/api/api.service';

/**
 * Componente per la barra di intestazione del coach.
 * Gestisce la visualizzazione del menu laterale, del profilo e la disconnessione.
 * @export
 * @class HeaderCoachComponent
 */
@Component({
  selector: 'app-header-coach',
  standalone: true,
  imports: [SidebarComponent],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderCoachComponent {
  /**
   * Flag per la visualizzazione del menu laterale.
   * @type {boolean}
   * @memberof HeaderCoachComponent
   */
  sidebarOn: boolean = false;

  /**
   * Flag per la visualizzazione del profilo.
   * @type {boolean}
   * @memberof HeaderCoachComponent
   */
  profileOn: boolean = false;

  /**
   * Crea un'istanza di HeaderCoachComponent.
   * @param {ApiService} apiService - Servizio per le chiamate API.
   * @param {RedirectService} redirectService - Servizio per la gestione delle navigazioni.
   * @memberof HeaderCoachComponent
   */
  constructor(
    private apiService: ApiService,
    private redirectService: RedirectService
  ) {}

  /**
   * Alterna la visibilità del menu laterale.
   * Se il profilo è aperto, lo chiude prima di aprire il menu laterale.
   * @memberof HeaderCoachComponent
   */
  toggleMenu() {
    console.log('clicked menu');
    if (this.profileOn) {
      this.profileOn = false;
    }
    this.sidebarOn = !this.sidebarOn;
  }

  /**
   * Alterna la visibilità del profilo.
   * Se il menu laterale è aperto, lo chiude prima di aprire il profilo.
   * @memberof HeaderCoachComponent
   */
  toggleProfile() {
    console.log('clicked profile');
    if (this.sidebarOn) {
      this.sidebarOn = false;
    }
    this.profileOn = !this.profileOn;
  }

  /**
   * Gestisce la disconnessione dell'utente.
   * Invia una richiesta di logout al server e rimuove i dati di sessione.
   * @memberof HeaderCoachComponent
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
   * @param {string} route - La rotta verso cui navigare.
   * @memberof HeaderCoachComponent
   */
  navigateTo(route: string): void {
    this.redirectService.setRedirect(route);
  }
}
