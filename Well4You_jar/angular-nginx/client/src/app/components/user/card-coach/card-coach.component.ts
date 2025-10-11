import { Component, HostListener, Input, OnInit } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { CommonModule } from '@angular/common';

import { CardCoach } from '../../../interfaces/card-coach';

import { ApiService } from '../../../services/api/api.service';
import { RedirectService } from '../../../services/redirect/redirect.service';

@Component({
  selector: 'app-card-coach',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './card-coach.component.html',
  styleUrl: './card-coach.component.scss'
})
export class CardCoachComponent implements OnInit {

  @Input() card!: CardCoach;
  // card: CardCoach = this.getCard()
  expanded = false

  constructor(
    private apiService: ApiService,
    private redirectService: RedirectService
  ) {}

  ngOnInit(): void {
    // this.card = this.getCard()
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

  async toggleExpand(event: Event) {
    event.stopPropagation();
    this.expanded = !this.expanded;
    if (this.expanded === true) {
      try {
        const coachId = this.card.id;
        const params = new HttpParams().set('coachId', coachId!);
        const response: any = await this.apiService.get('api/coach/get-coach', params).toPromise();
        this.card.full = response;
      } catch (error) {
        console.error('Showcase error', error);
      }
    }
  }

  reservation(event: Event) {
    event.stopPropagation()
    sessionStorage.setItem('coach_id', this.card.id.toString())
    this.navigateTo('book-call')
  }

  stopPropagation(event: Event) {
    event.stopPropagation()
  }

  navigateTo(route: string) {
    this.redirectService.setRedirect(route)
  }

}