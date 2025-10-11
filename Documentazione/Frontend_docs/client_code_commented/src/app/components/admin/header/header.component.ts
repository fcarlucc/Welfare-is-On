import { Component } from '@angular/core';

import { RedirectService } from '../../../services/redirect/redirect.service';
import { ApiService } from '../../../services/api/api.service';

/**
 * Componente per l'header dell'amministratore.
 * @export
 * @class HeaderAdminComponent
 */
@Component({
  selector: 'app-header-admin',
  standalone: true,
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderAdminComponent {
  /**
   * Flag per mostrare/nascondere il profilo.
   * @type {boolean}
   * @memberof HeaderAdminComponent
   */
  profileOn: boolean = false;

  /**
   * Crea un'istanza di HeaderAdminComponent.
   * @param {ApiService} apiService
   * @param {RedirectService} redirectService
   * @memberof HeaderAdminComponent
   */
  constructor(
    private apiService: ApiService,
    private redirectService: RedirectService
  ) {}

  /**
   * Alterna la visibilità del profilo.
   * @memberof HeaderAdminComponent
   */
  toggleProfile(): void {
    console.log('clicked profile');
    this.profileOn = !this.profileOn;
  }

  /**
   * Esegue il logout dell'utente.
   * @memberof HeaderAdminComponent
   */
  signOut(): void {
    try {
      this.apiService.post('api/auth/logout', {}).toPromise();
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
   * Naviga verso una determinata route.
   * @param {string} route
   * @memberof HeaderAdminComponent
   */
  navigateTo(route: string): void {
    this.redirectService.setRedirect(route);
  }
}
