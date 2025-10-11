import { AfterViewInit, Component } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SectionsComponent } from './sections/sections.component';
import { RedirectService } from '../../services/redirect/redirect.service';

/**
 * Componente per la sezione pre-intestazione del sito web.
 * Gestisce la navigazione a scorrimento e l'attivazione del link corrente.
 * 
 * @export
 * @class PreHeaderComponent
 * @implements {AfterViewInit}
 */
@Component({
  selector: 'app-pre-header',
  standalone: true,
  imports: [CommonModule, SectionsComponent],
  templateUrl: './pre-header.component.html',
  styleUrls: ['./pre-header.component.scss']
})
export class PreHeaderComponent implements AfterViewInit {

  /** Indica se la navbar è collassata o meno. */
  isNavbarCollapsed = false;

  /** Sezione attualmente attiva nella visualizzazione. */
  activeSection = '';

  /** Indica se il menu a discesa è aperto o meno. */
  isDropdownOpen = false;

  /** NodeList di elementi di sezione nella pagina. */
  sections!: NodeListOf<HTMLElement>;

  /** NodeList di link di navigazione nell'intestazione. */
  navLinks!: NodeListOf<HTMLElement>;

  /** Indica se il reindirizzamento è abilitato. */
  redirectChild: boolean = false;

  /**
   * Crea un'istanza di PreHeaderComponent.
   * 
   * @param {RedirectService} redirectService - Servizio per la gestione della navigazione.
   */
  constructor(
    private redirectService: RedirectService
  ) {}

  /**
   * Metodo chiamato dopo che la vista è stata completamente inizializzata.
   * Gestisce l'attivazione del link corrente e l'osservazione delle sezioni nella pagina.
   * 
   * @memberof PreHeaderComponent
   */
  ngAfterViewInit(): void {
    this.sections = document.querySelectorAll('section');
    this.navLinks = document.querySelectorAll('.header a'); // Aggiornato il selettore

    // Aggiungi event listener ai link di navigazione per eventi di clic
    this.navLinks.forEach(link => {
      link.addEventListener('click', (event) => {
        event.preventDefault();
        const targetId = link.getAttribute('href')?.substring(1);
        if (targetId) {
          document.getElementById(targetId)?.scrollIntoView({ behavior: 'smooth' });
          this.activeSection = targetId;
          this.updateActiveLink();
        }
      });
    });

    // Opzioni per l'osservatore di intersezione
    const observerOptions = {
      root: null,
      rootMargin: '20px',
      threshold: 0.5 // Regola la soglia come necessario
    };

    // Callback dell'osservatore per aggiornare la sezione attiva
    const observerCallback = (entries: IntersectionObserverEntry[]) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          this.activeSection = entry.target.id;
          this.updateActiveLink();
        }
      });
    };

    const observer = new IntersectionObserver(observerCallback, observerOptions);
    this.sections.forEach(section => observer.observe(section));
  }

  /**
   * Aggiorna il link attivo nella barra di navigazione.
   * 
   * @memberof PreHeaderComponent
   */
  updateActiveLink() {
    this.navLinks.forEach(link => {
      if (link.getAttribute('href')?.substring(1) === this.activeSection) {
        link.classList.add('active');
      } else {
        link.classList.remove('active');
      }
    });
  }

  /**
   * Verifica se l'utente è in una delle pagine di autenticazione.
   * 
   * @returns {boolean} - Indica se l'utente è in una delle pagine di autenticazione.
   * @memberof PreHeaderComponent
   */
  isSigning(): boolean {
    console.log('current path:', window.location.pathname);
    return [
      '/sign-in',
      '/sign-in/2FA',
      '/forgot-password',
      '/reset-password',
      '/sign-up',
      '/sign-up/survey',
      '/sign-up/2FA'
    ].includes(window.location.pathname);
  }

  /**
   * Torna alla pagina precedente nella cronologia del browser e ricarica la pagina.
   * 
   * @memberof PreHeaderComponent
   */
  goBack() {
    window.history.back();
    setTimeout(() => {
      window.location.reload();
    }, 100);
  }

  /**
   * Gestisce la navigazione verso una rotta specificata.
   * 
   * @param {string} route - La rotta verso cui navigare.
   * @memberof PreHeaderComponent
   */
  navigateTo(route: string) {
    this.redirectService.setRedirect(route);
  }
}
