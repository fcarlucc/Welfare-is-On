import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpParams } from '@angular/common/http';
import { SafeUrl } from '@angular/platform-browser';

import { CoachSectionComponent } from '../coach-section/coach-section.component';

import { CoachData } from '../../../interfaces/coach-data';

import { ApiService } from '../../../services/api/api.service';
import { ImageService } from '../../../services/image/image.service';
import { MapsService } from '../../../services/maps/maps.service';

/**
 * Componente per visualizzare i dati dei coach nella sezione di welfare.
 * 
 * @export
 * @class WelfareCoachComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-welfare-coach',
  standalone: true,
  imports: [CommonModule, CoachSectionComponent],
  templateUrl: './welfare-coach.component.html',
  styleUrls: ['./welfare-coach.component.scss']
})
export class WelfareCoachComponent implements OnInit {
  
  /**
   * Dati dei coach da visualizzare.
   * 
   * @type {CoachData}
   * @memberof WelfareCoachComponent
   */
  allCoaches!: CoachData;

  /**
   * Crea un'istanza di WelfareCoachComponent.
   * 
   * @param {ApiService} apiService - Servizio per le chiamate API.
   * @param {ImageService} imageService - Servizio per la gestione delle immagini.
   * @param {MapsService} mapsService - Servizio per le informazioni geografiche.
   * @memberof WelfareCoachComponent
   */
  constructor(
    private apiService: ApiService,
    private imageService: ImageService,
    private mapsService: MapsService
  ) {}

  /**
   * Inizializza il componente. Recupera i dati dei coach e li elabora.
   * 
   * @memberof WelfareCoachComponent
   */
  async ngOnInit(): Promise<void> {
    const userId = sessionStorage.getItem('user_id');
    const params = new HttpParams().set('userId', userId!);

    try {
      const response: any = await this.apiService.get('api/coach/showcase-coach', params).toPromise();
      console.log('Showcase coach successful', response);
      this.allCoaches = response;
      console.log('showcase = ', this.allCoaches.sections);
      console.log('image id = ' + this.allCoaches.sections[0].infoShowCaseDto[0].imageId);

      // Elenco dei dati di ogni coach e recupero delle informazioni geografiche e delle immagini.
      for (let section of this.allCoaches.sections) {
        for (let card of section.infoShowCaseDto) {
          // Recupera l'indirizzo basato sulle coordinate GPS.
          const city = await this.mapsService.getAddress(card.locationDto!.latitude, card.locationDto!.longitude);
          card.location = city.results[0].address_components[2].long_name;

          // Recupera l'URL dell'immagine per ogni coach.
          const imageUrl: SafeUrl = await this.imageService.fetchImage(card.imageId);
          card.imgUrl = imageUrl;
        }
      }

    } catch (error) {
      console.error('Showcase coach error', error);
    }
  }
}
