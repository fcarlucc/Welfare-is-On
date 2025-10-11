import { Component, HostListener, ViewChild, ElementRef, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpParams } from '@angular/common/http';

import { CardService } from '../../../interfaces/card-service';
import { ApiService } from '../../../services/api/api.service';

/**
 * Componente per la visualizzazione dei dettagli di un servizio.
 * Gestisce l'espansione della scheda, i commenti, i like e l'acquisto del servizio.
 * 
 * @export
 * @class CardServiceComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-card-service',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './card-service.component.html',
  styleUrls: ['./card-service.component.scss']
})
export class CardServiceComponent implements OnInit {

  @ViewChild('commentInput') commentInput?: ElementRef<HTMLTextAreaElement>;
  @Input() card!: CardService;

  expanded = false; // Indica se la scheda è espansa
  showComments = false; // Indica se i commenti sono visibili
  newComment = ''; // Contenuto del nuovo commento
  showCartButton: boolean = true; // Indica se il pulsante del carrello deve essere mostrato
  purchasedOn: boolean = false; // Indica se il servizio è stato acquistato

  /**
   * Crea un'istanza di CardServiceComponent.
   * @param {ApiService} apiService - Servizio per le chiamate API.
   */
  constructor(
    private apiService: ApiService,
  ) {}

  /**
   * Metodo chiamato all'inizializzazione del componente.
   * Attualmente non implementa logica aggiuntiva.
   * 
   * @memberof CardServiceComponent
   */
  ngOnInit(): void { }

  /**
   * Gestisce l'espansione della scheda del servizio e carica i dettagli del servizio se necessario.
   * 
   * @param {Event} event - Evento di clic.
   * @memberof CardServiceComponent
   */
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

  /**
   * Gestisce il clic al di fuori della scheda del servizio per chiudere l'espansione.
   * 
   * @param {Event} event - Evento di clic.
   * @memberof CardServiceComponent
   */
  @HostListener('document:click', ['$event'])
  clickOutside(event: Event) {
    if (this.expanded) {
      const target = event.target as HTMLElement;
      if (!target.closest('.service-card')) {
        this.expanded = false;
      }
    }
  }

  /**
   * Calcola il prezzo del servizio tenendo conto dello sconto.
   * 
   * @returns {number} - Prezzo finale dopo lo sconto.
   * @memberof CardServiceComponent
   */
  calculatePrice(): number {
    const price = this.card.full!.price;
    const discount = this.card.full!.discount;
    return price - (price * (discount / 100));
  }

  /**
   * Gestisce l'azione di like sul servizio.
   * Aumenta o diminuisce il numero di like a seconda dello stato corrente.
   * 
   * @param {Event} event - Evento di clic.
   * @memberof CardServiceComponent
   */
  async likeCard(event: Event) {
    event.stopPropagation();
    const userId = sessionStorage.getItem('user_id');
    const serviceId = this.card.id;
    if (this.card.full!.liked) {
      try {
        await this.apiService.post('api/like/delete', { 'userId': userId, 'serviceId': serviceId }).toPromise();
      } catch (error) {
        console.error('Showcase error', error);
      }
      this.card.full!.likes--;
    } else {
      try {
        await this.apiService.post('api/like/create', { 'userId': userId, 'serviceId': serviceId }).toPromise();
      } catch (error) {
        console.error('Showcase error', error);
      }
      this.card.full!.likes++;
    }
    this.card.full!.liked = !this.card.full!.liked;
  }

  /**
   * Mostra o nasconde la sezione dei commenti.
   * 
   * @param {Event} event - Evento di clic.
   * @memberof CardServiceComponent
   */
  toggleComments(event: Event) {
    event.stopPropagation();
    this.showComments = !this.showComments;
  }

  /**
   * Aggiorna il valore del nuovo commento.
   * 
   * @param {Event} event - Evento di input.
   * @memberof CardServiceComponent
   */
  updateNewComment(event: Event) {
    const target = event.target as HTMLTextAreaElement;
    this.newComment = target.value;
  }

  /**
   * Impedisce la propagazione dell'evento.
   * 
   * @param {Event} event - Evento di clic.
   * @memberof CardServiceComponent
   */
  stopPropagation(event: Event) {
    event.stopPropagation();
  }

  /**
   * Aggiunge un nuovo commento alla scheda del servizio.
   * 
   * @param {Event} event - Evento di clic.
   * @memberof CardServiceComponent
   */
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
        await this.apiService.post('api/comment/create', this.card.full!.comments[0]).toPromise();
      } catch (error) {
        console.error('Showcase error', error);
      }
      this.commentInput!.nativeElement.value = '';
      this.newComment = '';
    }
  }

  /**
   * Gestisce l'acquisto del servizio.
   * Aggiorna lo stato dell'acquisto e invia la richiesta al server.
   * 
   * @param {Event} event - Evento di clic.
   * @memberof CardServiceComponent
   */
  async purchaseService(event: Event) {
    event.stopPropagation();

    if (this.card.full!.purchased) {
      event.preventDefault();
      return;
    }
    const userId = sessionStorage.getItem('user_id');
    const serviceId = this.card.id;
    try {
      await this.apiService.post('api/shop/make-purchase', { 'userId': userId, 'serviceId': serviceId }).toPromise();
      this.purchasedOn = true;
    } catch (error) {
      console.error('Showcase error', error);
    }
  }

  /**
   * Crea un link per Google Maps basato su latitudine e longitudine.
   * 
   * @param {number} lat - Latitudine.
   * @param {number} lng - Longitudine.
   * @returns {string} - Link a Google Maps.
   * @memberof CardServiceComponent
   */
  getGoogleMapsLink(lat: number, lng: number): string {
    return `https://www.google.com/maps/search/?api=1&query=${lat},${lng}`;
  }

  /**
   * Verifica se la pagina corrente è l'endpoint specifico per gli acquisti.
   * 
   * @returns {boolean} - Indica se la pagina corrente è '/purchased'.
   * @memberof CardServiceComponent
   */
  isSpecificEndpoint(): boolean {
    return window.location.pathname === '/purchased';
  }
}
