import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { AuthService } from './services/auth/auth.service';

import { HeaderComponent } from './components/user/header/header.component';
import { HeaderCoachComponent } from './components/coach/header/header.component';
import { HeaderAdminComponent } from './components/admin/header/header.component';
import { PreHeaderComponent } from './components/pre-header/pre-header.component';
import { FooterComponent } from './components/footer/footer.component';

/**
 * Componente principale dell'applicazione.
 * Gestisce la visualizzazione dell'intestazione e del piè di pagina in base
 * allo stato di autenticazione dell'utente e alle sue autorizzazioni.
 */
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, PreHeaderComponent, HeaderComponent, HeaderCoachComponent, HeaderAdminComponent, FooterComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  /**
   * Titolo dell'applicazione.
   */
  title = 'client';

  /**
   * Crea un'istanza del componente `AppComponent`.
   * @param authService - Servizio per la gestione dell'autenticazione.
   */
  constructor(private authService: AuthService) {}

  /**
   * Verifica se l'utente è autenticato.
   * @returns `true` se l'utente è autenticato, altrimenti `false`.
   */
  isAuth(): boolean {
    return this.authService.isAuthenticated();
  }

  /**
   * Verifica se l'utente autenticato è un utente normale.
   * @returns `true` se l'utente autenticato è un utente normale, altrimenti `false`.
   */
  isAuthUser(): boolean {
    return this.authService.isAuthenticatedUser();
  }

  /**
   * Verifica se l'utente autenticato è un allenatore.
   * @returns `true` se l'utente autenticato è un allenatore, altrimenti `false`.
   */
  isAuthCoach(): boolean {
    return this.authService.isAuthenticatedCoach();
  }

  /**
   * Verifica se l'utente autenticato è un amministratore.
   * @returns `true` se l'utente autenticato è un amministratore, altrimenti `false`.
   */
  isAuthAdmin(): boolean {
    return this.authService.isAuthenticatedAdmin();
  }
}
