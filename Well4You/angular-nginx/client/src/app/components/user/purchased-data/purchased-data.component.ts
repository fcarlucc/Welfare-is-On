import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpParams } from '@angular/common/http';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

import { PurchasedSectionComponent } from './purchased-section/purchased-section.component';

import { PurchasedData } from '../../../interfaces/purchased-data';

import { ApiService } from '../../../services/api/api.service';
import { ImageService } from '../../../services/image/image.service';



@Component({
  selector: 'app-purchased-data',
  standalone: true,
  imports: [CommonModule, PurchasedSectionComponent],
  templateUrl: './purchased-data.component.html',
  styleUrl: './purchased-data.component.scss'
})
export class PurchasedDataComponent implements OnInit {
  purchased!: PurchasedData

  constructor(
    private apiService: ApiService,
    private imageService: ImageService
  ) {}

  async ngOnInit(): Promise<void> {
    const userId = sessionStorage.getItem('user_id');
    const params = new HttpParams().set('userId', userId!);

    try {
      const response: any = await this.apiService.get('api/services/get-purchased-showcase', params).toPromise();
      console.log('Purchased successful', response);
      this.purchased = response;
      console.log('purchased = ', this.purchased);
      console.log('image id = ' + this.purchased.sections[0].infoShowCaseDto[0].imageId);
      for (let section of this.purchased.sections) {
        for (let card of section.infoShowCaseDto) {
          const imageUrl: SafeUrl = await this.imageService.fetchImage(card.imageId);
          card.imgUrl = imageUrl
        }
      }
    } catch (error) {
      console.error('purchased error', error);
    }
  }
}