import { Component, OnInit } from '@angular/core';
import { Profile } from '../../../interfaces/profile';
import { RedirectService } from '../../../services/redirect/redirect.service';
import { HttpParams } from '@angular/common/http';
import { ApiService } from '../../../services/api/api.service';

/**
 * Componente per la visualizzazione del profilo utente.
 * 
 * @export
 * @class ProfileComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  /**
   * Profilo dell'utente.
   * 
   * @type {Profile}
   * @memberof ProfileComponent
   */
  profile!: Profile;

  /**
   * Crea un'istanza di ProfileComponent.
   * 
   * @param {ApiService} apiService - Servizio per le chiamate API.
   * @param {RedirectService} redirectService - Servizio per la gestione delle redirezioni.
   */
  constructor(
    private apiService: ApiService,
    private redirectService: RedirectService
  ) {}

  /**
   * Inizializza il componente.
   * Recupera le informazioni del profilo dell'utente e formatta la data di nascita.
   * 
   * @memberof ProfileComponent
   */
  async ngOnInit(): Promise<void> {
    // Recupera l'ID dell'utente dalla sessione.
    const userId = sessionStorage.getItem('user_id');
    const params = new HttpParams().set('userId', userId!);

    try {
      // Richiede le informazioni del profilo dall'API.
      const response: any = await this.apiService.get('api/user/info', params).toPromise();
      this.profile = response;

      // Format the date of birth to 'YYYY-MM-DD' format.
      this.profile.dob = this.profile.dob.split('T')[0];
    } catch (error) {
      console.error('Showcase error', error);
    }
  }

  /**
   * Verifica se la data di nascita esiste.
   * 
   * @returns {boolean} - Restituisce true se la data di nascita è presente, false altrimenti.
   * @memberof ProfileComponent
   */
  dobExist(): boolean {
    return !!this.profile.dob;
  }

  /**
   * Naviga verso una route specificata.
   * 
   * @param {string} route - La route verso cui navigare.
   * @memberof ProfileComponent
   */
  navigateTo(route: string): void {
    this.redirectService.setRedirect(route);
  }
}
