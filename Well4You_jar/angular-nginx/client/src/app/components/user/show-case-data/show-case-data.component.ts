import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { HttpParams } from '@angular/common/http';
import { CommonModule } from '@angular/common';

import { ApiService } from '../../../services/api/api.service';

import { ShowCaseData } from '../../../interfaces/show-case-data';

import { ShowCaseSectionComponent } from './show-case-section/show-case-section.component';
import { ImageService } from '../../../services/image/image.service';

@Component({
  selector: 'app-showcase-data',
  standalone: true,
  imports: [CommonModule, ShowCaseSectionComponent],
  templateUrl: './show-case-data.component.html',
  styleUrls: ['./show-case-data.component.scss']
})
export class ShowCaseDataComponent implements OnInit {

  showCase!: ShowCaseData

  constructor(
    private apiService: ApiService,
    private imageService: ImageService
  ) {}

  async ngOnInit(): Promise<void> {
    const userId = sessionStorage.getItem('user_id')
    const params = new HttpParams().set('userId', userId!)

    try {
      const response: any = await this.apiService.get('api/services/get-showcase', params).toPromise();
      console.log('Showcase successful', response);
      this.showCase = response;
      console.log('showcase = ', this.showCase.sections);
      console.log('image id = ' + this.showCase.sections[0].infoShowCaseDto[0].imageId);
      for (let section of this.showCase.sections) {
        for (let card of section.infoShowCaseDto) {
          const imageUrl: SafeUrl = await this.imageService.fetchImage(card.imageId);
          card.imgUrl = imageUrl
        }
      }
    } catch (error) {
      console.error('Showcase error', error);
    }
  }
}