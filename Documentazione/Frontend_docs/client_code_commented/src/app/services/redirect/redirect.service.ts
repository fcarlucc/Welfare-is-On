import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

/**
 * Servizio per gestire le operazioni di reindirizzamento all'interno dell'applicazione.
 * Permette di salvare una rotta di reindirizzamento e le relative opzioni, 
 * e di eseguire il reindirizzamento quando necessario.
 */
@Injectable({
  providedIn: 'root'
})
export class RedirectService {

  private redirectTo: string | undefined;
  private redirectExtras: any | undefined;

  /**
   * Crea un'istanza del servizio `RedirectService`.
   * @param router - Il router di Angular per gestire la navigazione.
   */
  constructor(
    private router: Router
  ) {}

  /**
   * Imposta la rotta di reindirizzamento e naviga verso di essa.
   * @param route - La rotta verso cui effettuare il reindirizzamento.
   */
  setRedirect(route: string): void {
    this.redirectTo = route;
    console.log(`Redirect to: ${route}`);
    this.router.navigate([route]);
  }

  /**
   * Recupera la rotta di reindirizzamento attualmente memorizzata.
   * @returns La rotta di reindirizzamento se impostata, altrimenti `undefined`.
   */
  getRedirect(): string | undefined {
    return this.redirectTo;
  }

  /**
   * Imposta la rotta di reindirizzamento e le opzioni aggiuntive, poi naviga verso di essa.
   * @param route - La rotta verso cui effettuare il reindirizzamento.
   * @param extras - Opzioni aggiuntive per la navigazione.
   */
  setRedirectPlus(route: string, extras: any): void { 
    this.redirectTo = route;
    this.redirectExtras = extras;
    console.log(`Redirect to: ${route} with data:`, extras);
    this.router.navigate([route], this.redirectExtras);
  }

  /**
   * Recupera i dati aggiuntivi della navigazione corrente.
   * @param key - La chiave per accedere ai dati aggiuntivi.
   * @returns I dati aggiuntivi associati alla chiave se disponibili, altrimenti `undefined`.
   */
  getRedirectPlus(key: string): any | undefined {
    const navigation = this.router.getCurrentNavigation();
    
    if (navigation?.extras?.state) {
      return navigation.extras.state[key];
    }
    return undefined;
  }

  /**
   * Esegue la navigazione verso la rotta memorizzata e ripristina lo stato.
   * Se non è stato impostato alcun redirect, non effettua alcuna azione.
   */
  navigate(): void { 
    if (this.redirectTo) {
      this.router.navigate([this.redirectTo], this.redirectExtras);
      this.redirectTo = undefined;
      this.redirectExtras = undefined;
    }
  }
}
