import { Component } from '@angular/core';
import { CardSection } from '../../../../interfaces/card-section';
import { ApiService } from '../../../../services/api/api.service';
import { ImageService } from '../../../../services/image/image.service';
import { HttpParams } from '@angular/common/http';
import { SafeUrl } from '@angular/platform-browser';
import { CardSectionComponent } from '../../card-section/card-section.component';

@Component({
  selector: 'app-physical',
  standalone: true,
  imports: [CardSectionComponent],
  templateUrl: './physical.component.html',
  styleUrl: './physical.component.scss'
})
export class PhysicalComponent {

  section!: CardSection

  constructor(
    private apiService: ApiService,
    private imageService: ImageService
  ) {}

  async ngOnInit(): Promise<void> {
    const userId = sessionStorage.getItem('user_id')
    const params = new HttpParams().set('userId', userId!).set('section', 'Physical')

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
