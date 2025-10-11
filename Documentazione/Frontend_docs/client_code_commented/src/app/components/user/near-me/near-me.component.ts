import { Component, OnInit } from '@angular/core';
import { MapComponent } from '../map/map.component';
import { CardSectionComponent } from '../card-section/card-section.component';
import { MapsService } from '../../../services/maps/maps.service';
import { CardSection } from '../../../interfaces/card-section';
import { ApiService } from '../../../services/api/api.service';
import { ImageService } from '../../../services/image/image.service';
import { HttpParams } from '@angular/common/http';
import { SafeUrl } from '@angular/platform-browser';

/**
 * Componente per la visualizzazione dei servizi nelle vicinanze dell'utente.
 * 
 * @export
 * @class NearMeComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-near-me',
  standalone: true,
  imports: [MapComponent, CardSectionComponent],
  templateUrl: './near-me.component.html',
  styleUrls: ['./near-me.component.scss']
})
export class NearMeComponent implements OnInit {
  
  /**
   * Coordinate geografiche dell'utente.
   * 
   * @type {{ lat: number, lng: number }}
   * @memberof NearMeComponent
   */
  coordinates!: { lat: number, lng: number };

  /**
   * Sezione dei servizi nelle vicinanze.
   * 
   * @type {CardSection}
   * @memberof NearMeComponent
   */
  section!: CardSection;

  /**
   * Crea un'istanza di NearMeComponent.
   * 
   * @param {MapsService} mapsService - Servizio per ottenere la posizione dell'utente.
   * @param {ApiService} apiService - Servizio per le chiamate API.
   * @param {ImageService} imageService - Servizio per recuperare le immagini.
   */
  constructor(
    private mapsService: MapsService,
    private apiService: ApiService,
    private imageService: ImageService
  ) {}

  /**
   * Inizializza il componente.
   * Recupera la posizione dell'utente e i servizi nelle vicinanze, e aggiorna le immagini dei servizi.
   * 
   * @memberof NearMeComponent
   */
  async ngOnInit(): Promise<void> {
    // Recupera la posizione dell'utente.
    this.mapsService.getUserLocation().then(location => {
      this.coordinates = { lat: location.latitude!, lng: location.longitude! };
    }).catch(error => {
      console.error('Error getting user position:', error);
      this.coordinates = { lat: 0, lng: 0 };
    });

    // Recupera i servizi nella sezione "Near-me" per l'utente.
    const userId = sessionStorage.getItem('user_id');
    const params = new HttpParams().set('userId', userId!).set('section', 'Near-me');

    try {
      const response: any = await this.apiService.get('api/services/get-section-services', params).toPromise();
      console.log('Showcase successful', response);
      this.section = response;
      console.log('showcase = ', this.section);

      // Recupera e assegna le immagini per ciascun servizio nella sezione.
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
