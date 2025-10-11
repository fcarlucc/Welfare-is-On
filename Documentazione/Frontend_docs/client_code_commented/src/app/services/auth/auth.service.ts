import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';

/**
 * Servizio per la gestione dell'autenticazione e delle informazioni dell'utente.
 * Questo servizio gestisce il controllo dei ruoli dell'utente e le operazioni di accesso e disconnessione.
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private apiService: ApiService
  ) { }

  /**
   * Verifica se l'utente è autenticato.
   * Controlla se l'utente è autenticato come utente, allenatore o amministratore.
   * @returns `true` se l'utente è autenticato, altrimenti `false`.
   */
  isAuthenthicated(): boolean {
    return this.isAuthenthicatedUser() || this.isAuthenthicatedCoach() || this.isAuthenthicatedAdmin();
  }

  /**
   * Verifica se l'utente è autenticato come utente.
   * @returns `true` se il ruolo dell'utente è "ROLE_USER", altrimenti `false`.
   */
  isAuthenthicatedUser(): boolean {
    const role = sessionStorage.getItem('role');
    return role === "ROLE_USER";
  }

  /**
   * Verifica se l'utente è autenticato come allenatore.
   * @returns `true` se il ruolo dell'utente è "ROLE_COACH", altrimenti `false`.
   */
  isAuthenthicatedCoach(): boolean {
    const role = sessionStorage.getItem('role');
    return role === "ROLE_COACH";
  }

  /**
   * Verifica se l'utente è autenticato come amministratore.
   * @returns `true` se il ruolo dell'utente è "ROLE_ADMIN", altrimenti `false`.
   */
  isAuthenthicatedAdmin(): boolean {
    const role = sessionStorage.getItem('role');
    return role === "ROLE_ADMIN";
  }

  /**
   * Imposta le informazioni del token di accesso nell'archiviazione di sessione.
   * Estrae le informazioni dal token JWT e le memorizza in sessionStorage.
   * @param accessToken - Il token di accesso JWT.
   */
  setTokenInfo(accessToken: string) {
    const payload = JSON.parse(atob(accessToken.split('.')[1]));
    const email = payload.sub;
    const userId = payload.userId;
    const fullName = payload.fullName;
    const role = payload.roles[0].authority;

    sessionStorage.setItem('access_token', accessToken);
    sessionStorage.setItem('user_id', userId);
    sessionStorage.setItem('email', email);
    sessionStorage.setItem('full_name', fullName);
    sessionStorage.setItem('role', role);
  }

  /**
   * Effettua il logout dell'utente e rimuove le informazioni di autenticazione.
   * Invia una richiesta di logout al server e rimuove tutte le informazioni di autenticazione da sessionStorage.
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
      console.error('Logout error', error);
    }
  }

}
