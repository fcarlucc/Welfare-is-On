import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpParams } from '@angular/common/http';
import { SafeUrl } from '@angular/platform-browser';

import { CoachSectionComponent } from '../coach-section/coach-section.component';

import { CoachData } from '../../../interfaces/coach-data';

import { ApiService } from '../../../services/api/api.service';
import { ImageService } from '../../../services/image/image.service';
import { MapsService } from '../../../services/maps/maps.service';

@Component({
  selector: 'app-welfare-coach',
  standalone: true,
  imports: [CommonModule, CoachSectionComponent],
  templateUrl: './welfare-coach.component.html',
  styleUrl: './welfare-coach.component.scss'
})
export class WelfareCoachComponent implements OnInit {

  allCoaches!: CoachData
  constructor(
    private apiService: ApiService,
    private imageService: ImageService,
    private mapsService: MapsService
  ) {}

  async ngOnInit(): Promise<void> {

    const userId = sessionStorage.getItem('user_id')
    const params = new HttpParams().set('userId', userId!)

    try {
      const response: any = await this.apiService.get('api/coach/showcase-coach', params).toPromise();
      console.log('Showcase coach successful', response);
      this.allCoaches = response;
      console.log('showcase = ', this.allCoaches.sections);
      console.log('image id = ' + this.allCoaches.sections[0].infoShowCaseDto[0].imageId);

      for (let section of this.allCoaches.sections) {
        for (let card of section.infoShowCaseDto) {
          const city = await this.mapsService.getAddress(card.locationDto!.latitude, card.locationDto!.longitude)
          card.location = city.results[0].address_components[2].long_name
        }
        for (let card of section.infoShowCaseDto) {
          const imageUrl: SafeUrl = await this.imageService.fetchImage(card.imageId);
          card.imgUrl = imageUrl
          // card.location = 'Repubblica dominicana del tronto della valletta'
        }
      }

    } catch (error) {
      console.error('Showcase coach error', error);
    }
  }
}
