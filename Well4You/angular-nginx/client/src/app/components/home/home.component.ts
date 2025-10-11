import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { HeaderComponent } from '../user/header/header.component';

import { RedirectService } from '../../services/redirect/redirect.service';
import { MapsService } from '../../services/maps/maps.service';
import { AuthService } from '../../services/auth/auth.service';
import { CardSectionComponent } from '../user/card-section/card-section.component';
import { CardSection } from '../../interfaces/card-section';
import { HttpParams } from '@angular/common/http';
import { ApiService } from '../../services/api/api.service';
import { SafeUrl } from '@angular/platform-browser';
import { ImageService } from '../../services/image/image.service';

@Component({
  selector: 'app-home',
  standalone: true,
  schemas: [],
  imports: [HeaderComponent, CardSectionComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  title = 'client'
  section!: CardSection

  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private apiService: ApiService,
    private mapsService: MapsService,
    private redirectService: RedirectService,
    private imageService: ImageService
  ) {}

  async ngOnInit(): Promise <void> {
    this.typeDynamicText()
    this.route.queryParams
      .subscribe(params => {
        if (params["token"] !== undefined) {
          console.log('authenticated with google sending location...')
          const token = params["token"]
          this.authService.setTokenInfo(token)
          this.mapsService.getUserPosition(sessionStorage.getItem('email')!)
        }
      }
    );
    if (this.showHome()) {
      const userId = sessionStorage.getItem('user_id')
      const params = new HttpParams().set('userId', userId!).set('section', 'For-me')

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

  showHome() {
    return this.authService.isAuthenthicated()
  }

  isUserAuthenticated() {
    return this.authService.isAuthenthicatedUser()
  }

  typeDynamicText(): void {
    const textElement = document.getElementById('dynamic-text')
    const text = "Benvenuti nel Portale del Welfare Aziendale"
    let index = 0

    if (textElement) {
      function typeText() {
        if (index < text.length) {
          textElement!.innerHTML += text.charAt(index)
          index++
          setTimeout(typeText, 50)
        }
      }
      typeText()
    }
  }

  navigateTo(route: string) {
    this.redirectService.setRedirect(route);
  }

}