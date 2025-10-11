import { Component, OnInit } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { SafeUrl } from '@angular/platform-browser';

import { CardSection } from '../../../../interfaces/card-section';

import { CardSectionComponent } from '../../card-section/card-section.component';

import { ApiService } from '../../../../services/api/api.service';
import { ImageService } from '../../../../services/image/image.service';

/**
 * Componente per la visualizzazione dei servizi psicologici.
 * 
 * @export
 * @class PsychologicalComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-psychological',
  standalone: true,
  imports: [CardSectionComponent],
  templateUrl: './psychological.component.html',
  styleUrls: ['./psychological.component.scss']
})
export class PsychologicalComponent implements OnInit {

  /**
   * Sezione dei servizi psicologici.
   * 
   * @type {CardSection}
   * @memberof PsychologicalComponent
   */
  section!: CardSection;

  /**
   * Crea un'istanza di PsychologicalComponent.
   * 
   * @param {ApiService} apiService - Servizio per le chiamate API.
   * @param {ImageService} imageService - Servizio per recuperare le immagini.
   */
  constructor(
    private apiService: ApiService,
    private imageService: ImageService
  ) {}

  /**
   * Inizializza il componente.
   * Recupera i servizi psicologici per l'utente e aggiorna le immagini dei servizi.
   * 
   * @memberof PsychologicalComponent
   */
  async ngOnInit(): Promise<void> {
    // Recupera l'ID dell'utente dalla sessione.
    const userId = sessionStorage.getItem('user_id');
    const params = new HttpParams().set('userId', userId!).set('section', 'Psychological');

    try {
      // Richiede i servizi psicologici dall'API.
      const response: any = await this.apiService.get('api/services/get-section-services', params).toPromise();
      console.log('Showcase successful', response);
      this.section = response;
      console.log('showcase = ', this.section);

      // Recupera e assegna le immagini per ciascun servizio nella sezione psicologica.
      console.log('image id = ' + this.section.infoShowCaseDto[0].imageId);
      for (let card of this.section.infoShowCaseDto) {
        const imageUrl: SafeUrl = await this.imageService.fetchImage(card.imageId);
        card.imgUrl = imageUrl;
      }
    } catch (error) {
      console.error('Showcase error', error);
    }
  }
}
