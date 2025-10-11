import { Component, OnInit } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { SafeUrl } from '@angular/platform-browser';

import { CardSection } from '../../../../interfaces/card-section';

import { CardSectionComponent } from '../../card-section/card-section.component';

import { ApiService } from '../../../../services/api/api.service';
import { ImageService } from '../../../../services/image/image.service';

/**
 * Componente per la visualizzazione dei servizi per le famiglie.
 * 
 * @export
 * @class FamilyComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-family',
  standalone: true,
  imports: [CardSectionComponent],
  templateUrl: './family.component.html',
  styleUrls: ['./family.component.scss']
})
export class FamilyComponent implements OnInit {

  /**
   * Sezione dei servizi per le famiglie.
   * 
   * @type {CardSection}
   * @memberof FamilyComponent
   */
  section!: CardSection;

  /**
   * Crea un'istanza di FamilyComponent.
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
   * Recupera i servizi per le famiglie per l'utente e aggiorna le immagini dei servizi.
   * 
   * @memberof FamilyComponent
   */
  async ngOnInit(): Promise<void> {
    // Recupera l'ID dell'utente dalla sessione.
    const userId = sessionStorage.getItem('user_id');
    const params = new HttpParams().set('userId', userId!).set('section', 'Family');

    try {
      // Richiede i servizi per le famiglie dall'API.
      const response: any = await this.apiService.get('api/services/get-section-services', params).toPromise();
      console.log('Showcase successful', response);
      this.section = response;
      console.log('showcase = ', this.section);

      // Recupera e assegna le immagini per ciascun servizio nella sezione famiglie.
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
