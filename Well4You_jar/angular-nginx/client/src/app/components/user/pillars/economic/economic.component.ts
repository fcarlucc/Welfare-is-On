import { Component, OnInit } from '@angular/core';
import { CardSection } from '../../../../interfaces/card-section';
import { ApiService } from '../../../../services/api/api.service';
import { CardSectionComponent } from '../../card-section/card-section.component';
import { HttpParams } from '@angular/common/http';
import { SafeUrl } from '@angular/platform-browser';
import { ImageService } from '../../../../services/image/image.service';

@Component({
  selector: 'app-economic',
  standalone: true,
  imports: [CardSectionComponent],
  templateUrl: './economic.component.html',
  styleUrl: './economic.component.scss'
})
export class EconomicComponent implements OnInit {

  section!: CardSection

  constructor(
    private apiService: ApiService,
    private imageService: ImageService
  ) {}

  async ngOnInit(): Promise<void> {
    const userId = sessionStorage.getItem('user_id')
    const params = new HttpParams().set('userId', userId!).set('section', 'Economic')

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
