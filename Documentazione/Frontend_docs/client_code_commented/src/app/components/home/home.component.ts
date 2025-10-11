import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SafeUrl } from '@angular/platform-browser';
import { HttpParams } from '@angular/common/http';

import { RedirectService } from '../../services/redirect/redirect.service';
import { MapsService } from '../../services/maps/maps.service';
import { ApiService } from '../../services/api/api.service';
import { ImageService } from '../../services/image/image.service';
import { AuthService } from '../../services/auth/auth.service';

import { HeaderComponent } from '../user/header/header.component';
import { CardSectionComponent } from '../user/card-section/card-section.component';

import { CardSection } from '../../interfaces/card-section';

/**
 * Componente per la pagina principale dell'applicazione.
 * Gestisce la visualizzazione della sezione dinamica basata sull'autenticazione dell'utente.
 * @export
 * @class HomeComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [HeaderComponent, CardSectionComponent],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  title = 'client'; // Titolo dell'applicazione
  section!: CardSection; // Sezione dinamica da visualizzare

  /**
   * Crea un'istanza di HomeComponent.
   * @param {ActivatedRoute} route - Servizio per la gestione dei parametri della rotta.
   * @param {AuthService} authService - Servizio di autenticazione dell'utente.
   * @param {ApiService} apiService - Servizio per le chiamate API.
   * @param {MapsService} mapsService - Servizio per la gestione delle mappe e della posizione dell'utente.
   * @param {RedirectService} redirectService - Servizio per la gestione della navigazione.
   * @param {ImageService} imageService - Servizio per la gestione delle immagini.
   */
  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private apiService: ApiService,
    private mapsService: MapsService,
    private redirectService: RedirectService,
    private imageService: ImageService
  ) {}

  /**
   * Metodo chiamato all'inizializzazione del componente.
   * Gestisce la logica di autenticazione, l'aggiornamento della sezione dinamica e la tipizzazione del testo.
   * @memberof HomeComponent
   */
  async ngOnInit(): Promise<void> {
    this.typeDynamicText(); // Inizia la tipizzazione del testo dinamico

    // Gestisce i parametri di query per l'autenticazione con Google
    this.route.queryParams.subscribe(params => {
      if (params["token"] !== undefined) {
        console.log('authenticated with google sending location...');
        const token = params["token"];
        this.authService.setTokenInfo(token);
        this.mapsService.getUserPosition(sessionStorage.getItem('email')!);
      }
    });

    // Verifica se l'utente è autenticato e aggiorna la sezione dinamica
    if (this.showHome()) {
      const userId = sessionStorage.getItem('user_id');
      const params = new HttpParams().set('userId', userId!).set('section', 'For-me');

      try {
        // Recupera i servizi della sezione
        const response: any = await this.apiService.get('api/services/get-section-services', params).toPromise();
        console.log('Showcase successful', response);
        this.section = response;
        console.log('showcase = ', this.section);
        console.log('image id = ' + this.section.infoShowCaseDto[0].imageId);

        // Recupera le immagini per le card
        for (let card of this.section.infoShowCaseDto) {
          const imageUrl: SafeUrl = await this.imageService.fetchImage(card.imageId);
          card.imgUrl = imageUrl;
        }
      } catch (error) {
        console.error('Showcase error', error);
      }
    }
  }

  /**
   * Verifica se l'utente è autenticato.
   * @returns {boolean} - Indica se l'utente è autenticato o meno.
   * @memberof HomeComponent
   */
  showHome() {
    return this.authService.isAuthenthicated();
  }

  /**
   * Verifica se l'utente è autenticato come utente normale.
   * @returns {boolean} - Indica se l'utente è autenticato come utente normale.
   * @memberof HomeComponent
   */
  isUserAuthenticated() {
    return this.authService.isAuthenthicatedUser();
  }

  /**
   * Gestisce la tipizzazione del testo dinamico nella pagina principale.
   * @memberof HomeComponent
   */
  typeDynamicText(): void {
    const textElement = document.getElementById('dynamic-text');
    const text = "Benvenuti nel Portale del Welfare Aziendale";
    let index = 0;

    if (textElement) {
      function typeText() {
        if (index < text.length) {
          textElement!.innerHTML += text.charAt(index);
          index++;
          setTimeout(typeText, 50);
        }
      }
      typeText();
    }
  }

  /**
   * Gestisce la navigazione verso una rotta specificata.
   * @param {string} route - La rotta verso cui navigare.
   * @memberof HomeComponent
   */
  navigateTo(route: string) {
    this.redirectService.setRedirect(route);
  }
}
