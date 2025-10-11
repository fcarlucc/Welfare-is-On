import { Component, OnInit, HostListener, ViewChild, ElementRef, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpParams } from '@angular/common/http';

import { CardService } from '../../../interfaces/card-service';

import { ApiService } from '../../../services/api/api.service';

@Component({
  selector: 'app-card-service',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './card-service.component.html',
  styleUrl: './card-service.component.scss'
})
export class CardServiceComponent  implements OnInit {
  @ViewChild('commentInput') commentInput?: ElementRef<HTMLTextAreaElement>;
  @Input() card!: CardService;

  expanded = false;
  showComments = false;
  newComment = '';
  showCartButton: boolean = true;
  purchasedOn: boolean = false

  constructor(
    private apiService: ApiService,
  ) {}

  ngOnInit(): void { }

  async toggleExpand(event: Event) {
    event.stopPropagation();
    this.expanded = !this.expanded;
    this.showComments = false;
    if (this.expanded === true) {
      try {
        const userId = sessionStorage.getItem('user_id');
        const serviceId = this.card.id;
        const params = new HttpParams().set('userId', userId!).set('serviceId', serviceId!);
        const response: any = await this.apiService.get('api/services/get-service', params).toPromise();
        this.card.full = response;
      } catch (error) {
        console.error('Showcase error', error);
      }
    }
  }

  @HostListener('document:click', ['$event'])
  clickOutside(event: Event) {
    if (this.expanded) {
      const target = event.target as HTMLElement;
      if (!target.closest('.service-card')) {
        this.expanded = false;
      }
    }
  }

  calculatePrice(): number {
    const price = this.card.full!.price;
    const discount = this.card.full!.discount;
    return price - (price * (discount / 100));
  }

  async likeCard(event: Event) {
    event.stopPropagation();
    const userId = sessionStorage.getItem('user_id');
    const serviceId = this.card.id;
    if (this.card.full!.liked) {
      try {
        this.apiService.post('api/like/delete', {'userId' : userId, 'serviceId' : serviceId}).toPromise();
      } catch (error) {
        console.error('Showcase error', error);
      }
      this.card.full!.likes--;
    } else {
      try {
        this.apiService.post('api/like/create', {'userId' : userId, 'serviceId' : serviceId}).toPromise();
      } catch (error) {
        console.error('Showcase error', error);
      }
      this.card.full!.likes++;
    }
    this.card.full!.liked = !this.card.full!.liked;
  }

  toggleComments(event: Event) {
    event.stopPropagation();
    this.showComments = !this.showComments;
  }

  updateNewComment(event: Event) {
    const target = event.target as HTMLTextAreaElement;
    this.newComment = target.value;
  }

  stopPropagation(event: Event) {
    event.stopPropagation();
  }

  async addComment(event: Event) {
    event.stopPropagation();
    if (this.newComment.trim()) {
      this.card.full!.comments.unshift({
        id: this.card.full!.comments.length + 1,
        serviceId: this.card.id,
        userId: +sessionStorage.getItem('user_id')!,
        fullName: sessionStorage.getItem('full_name')!,
        content: this.newComment,
        commentedAt: new Date()
      });
      try {
        this.apiService.post('api/comment/create', this.card.full!.comments[0]).toPromise();
      } catch (error) {
        console.error('Showcase error', error);
      }
      this.commentInput!.nativeElement.value = '';
      this.newComment = '';
    }
  }

  async purchaseService(event: Event) {
    event.stopPropagation();

    if (this.card.full!.purchased) {
      event.preventDefault()
      return
    }
    const userId = sessionStorage.getItem('user_id');
    const serviceId = this.card.id;
    try {
      this.apiService.post('api/shop/make-purchase', {'userId' : userId, 'serviceId' : serviceId}).toPromise();
      this.purchasedOn = true
    } catch (error) {
      console.error('Showcase error', error);
    }
  }

  getGoogleMapsLink(lat: number, lng: number): string {
    return `https://www.google.com/maps/search/?api=1&query=${lat},${lng}`;
  }

  isSpecificEndpoint(): boolean {
    return window.location.pathname === '/purchased';
  }
}
