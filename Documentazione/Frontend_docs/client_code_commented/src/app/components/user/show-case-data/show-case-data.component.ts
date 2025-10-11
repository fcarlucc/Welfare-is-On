import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { HttpParams } from '@angular/common/http';
import { CommonModule } from '@angular/common';

import { ApiService } from '../../../services/api/api.service';
import { ImageService } from '../../../services/image/image.service';

import { ShowCaseData } from '../../../interfaces/show-case-data';
import { ShowCaseSectionComponent } from './show-case-section/show-case-section.component';

/**
 * Componente per la visualizzazione dei dati di showcase.
 * 
 * @export
 * @class ShowCaseDataComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-showcase-data',
  standalone: true,
  imports: [CommonModule, ShowCaseSectionComponent],
  templateUrl: './show-case-data.component.html',
  styleUrls: ['./show-case-data.component.scss']
})
export class ShowCaseDataComponent implements OnInit {

  /**
   * Dati di showcase da visualizzare.
   * 
   * @type {ShowCaseData}
   * @memberof ShowCaseDataComponent
   */
  showCase!: ShowCaseData;

  /**
   * Crea un'istanza di ShowCaseDataComponent.
   * 
   * @param {ApiService} apiService - Servizio per le chiamate API.
   * @param {ImageService} imageService - Servizio per la gestione delle immagini.
   * @memberof ShowCaseDataComponent
   */
  constructor(
    private apiService: ApiService,
    private imageService: ImageService
  ) {}

  /**
   * Inizializza il componente e recupera i dati di showcase dall'API.
   * 
   * @returns {Promise<void>} - Una promessa che risolve quando i dati sono caricati.
   * @memberof ShowCaseDataComponent
   */
  async ngOnInit(): Promise<void> {
    const userId = sessionStorage.getItem('user_id');
    const params = new HttpParams().set('userId', userId!);

    try {
      const response: any = await this.apiService.get('api/services/get-showcase', params).toPromise();
      console.log('Showcase successful', response);
      this.showCase = response;
      console.log('showcase = ', this.showCase.sections);
      console.log('image id = ' + this.showCase.sections[0].infoShowCaseDto[0].imageId);
      
      // Carica le immagini per ciascun card nel showcase
      for (let section of this.showCase.sections) {
        for (let card of section.infoShowCaseDto) {
          const imageUrl: SafeUrl = await this.imageService.fetchImage(card.imageId);
          card.imgUrl = imageUrl;
        }
      }
    } catch (error) {
      console.error('Showcase error', error);
    }
  }
}
