import { Component, OnInit } from '@angular/core';
import { MapComponent } from '../map/map.component';
import { CardSectionComponent } from '../card-section/card-section.component';
import { MapsService } from '../../../services/maps/maps.service';
import { CardSection } from '../../../interfaces/card-section';
import { ApiService } from '../../../services/api/api.service';
import { ImageService } from '../../../services/image/image.service';
import { HttpParams } from '@angular/common/http';
import { SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-near-me',
  standalone: true,
  imports: [MapComponent, CardSectionComponent],
  templateUrl: './near-me.component.html',
  styleUrl: './near-me.component.scss'
})
export class NearMeComponent implements OnInit {
  coordinates!: { lat: number, lng: number };
  section!: CardSection


  constructor(
    private mapsService: MapsService,
    private apiService: ApiService,
    private imageService: ImageService
  ) {}

  async ngOnInit(): Promise<void> {
    this.mapsService.getUserLocation().then(location => {
      this.coordinates = { lat: location.latitude!, lng: location.longitude! };
    }).catch(error => {
      console.error('Error getting user position:', error);
      this.coordinates = { lat: 0, lng: 0 };
    });

    const userId = sessionStorage.getItem('user_id')
    const params = new HttpParams().set('userId', userId!).set('section', 'Near-me')

    try {
      const response: any = await this.apiService.get('api/services/get-section-services', params).toPromise();
      console.log('Showcase successful', response);
      this.section = response;
      console.log('showcase = ', this.section);
      console.log('image id = ' + this.section.infoShowCaseDto[0].imageId);
      for (let card of this.section.infoShowCaseDto) {
        const imageUrl: SafeUrl = await this.imageService.fetchImage(card.imageId);
        card.imgUrl = imageUrl
      }
    } catch (error) {
      console.error('Showcase error', error);
    }
  }

}
