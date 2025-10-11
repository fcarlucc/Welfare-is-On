import { Component, OnInit } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { SafeUrl } from '@angular/platform-browser';

import { CardSection } from '../../../../interfaces/card-section';

import { ImageService } from '../../../../services/image/image.service';

import { ApiService } from '../../../../services/api/api.service';
import { CardSectionComponent } from '../../card-section/card-section.component';

/**
 * Componente per la visualizzazione dei servizi economici.
 * 
 * @export
 * @class EconomicComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-economic',
  standalone: true,
  imports: [CardSectionComponent],
  templateUrl: './economic.component.html',
  styleUrls: ['./economic.component.scss']
})
export class EconomicComponent implements OnInit {

  /**
   * Sezione dei servizi economici.
   * 
   * @type {CardSection}
   * @memberof EconomicComponent
   */
  section!: CardSection;

  /**
   * Crea un'istanza di EconomicComponent.
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
   * Recupera i servizi economici per l'utente e aggiorna le immagini dei servizi.
   * 
   * @memberof EconomicComponent
   */
  async ngOnInit(): Promise<void> {
    // Recupera l'ID dell'utente dalla sessione.
    const userId = sessionStorage.getItem('user_id');
    const params = new HttpParams().set('userId', userId!).set('section', 'Economic');

    try {
      // Richiede i servizi economici dall'API.
      const response: any = await this.apiService.get('api/services/get-section-services', params).toPromise();
      console.log('Showcase successful', response);
      this.section = response;
      console.log('showcase = ', this.section);

      // Recupera e assegna le immagini per ciascun servizio nella sezione economica.
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
