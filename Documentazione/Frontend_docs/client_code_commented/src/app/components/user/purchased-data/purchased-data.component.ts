import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpParams } from '@angular/common/http';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

import { PurchasedSectionComponent } from './purchased-section/purchased-section.component';
import { PurchasedData } from '../../../interfaces/purchased-data';
import { ApiService } from '../../../services/api/api.service';
import { ImageService } from '../../../services/image/image.service';

/**
 * Componente per la visualizzazione dei dati acquistati dall'utente.
 * 
 * @export
 * @class PurchasedDataComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-purchased-data',
  standalone: true,
  imports: [CommonModule, PurchasedSectionComponent],
  templateUrl: './purchased-data.component.html',
  styleUrls: ['./purchased-data.component.scss']
})
export class PurchasedDataComponent implements OnInit {
  
  /**
   * Dati acquistati dall'utente.
   * 
   * @type {PurchasedData}
   * @memberof PurchasedDataComponent
   */
  purchased!: PurchasedData;

  /**
   * Crea un'istanza di PurchasedDataComponent.
   * 
   * @param {ApiService} apiService - Servizio per le chiamate API.
   * @param {ImageService} imageService - Servizio per la gestione delle immagini.
   */
  constructor(
    private apiService: ApiService,
    private imageService: ImageService
  ) {}

  /**
   * Inizializza il componente.
   * Recupera i dati acquistati dall'API e carica le immagini per ogni sezione.
   * 
   * @memberof PurchasedDataComponent
   */
  async ngOnInit(): Promise<void> {
    // Recupera l'ID dell'utente dalla sessione.
    const userId = sessionStorage.getItem('user_id');
    const params = new HttpParams().set('userId', userId!);

    try {
      // Richiede i dati acquistati dall'API.
      const response: any = await this.apiService.get('api/services/get-purchased-showcase', params).toPromise();
      console.log('Purchased successful', response);
      this.purchased = response;

      // Per ogni sezione e ogni card all'interno della sezione, carica l'immagine.
      for (let section of this.purchased.sections) {
        for (let card of section.infoShowCaseDto) {
          const imageUrl: SafeUrl = await this.imageService.fetchImage(card.imageId);
          card.imgUrl = imageUrl;
        }
      }
    } catch (error) {
      console.error('Purchased error', error);
    }
  }
}
