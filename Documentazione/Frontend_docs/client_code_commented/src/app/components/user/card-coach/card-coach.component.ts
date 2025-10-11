import { Component, HostListener, Input, OnInit } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { CommonModule } from '@angular/common';

import { CardCoach } from '../../../interfaces/card-coach';

import { ApiService } from '../../../services/api/api.service';
import { RedirectService } from '../../../services/redirect/redirect.service';

/**
 * Componente per la visualizzazione e gestione delle schede dei coach.
 * Mostra informazioni dettagliate su un coach e gestisce l'espansione e la prenotazione.
 * 
 * @export
 * @class CardCoachComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-card-coach',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './card-coach.component.html',
  styleUrls: ['./card-coach.component.scss']
})
export class CardCoachComponent implements OnInit {

  @Input() card!: CardCoach; // Scheda del coach da visualizzare
  expanded = false; // Stato di espansione della scheda

  /**
   * Crea un'istanza di CardCoachComponent.
   * 
   * @param {ApiService} apiService - Servizio per le chiamate API.
   * @param {RedirectService} redirectService - Servizio per la gestione della navigazione.
   */
  constructor(
    private apiService: ApiService,
    private redirectService: RedirectService
  ) {}

  /**
   * Metodo chiamato all'inizializzazione del componente.
   * Attualmente non implementa alcuna logica specifica.
   * 
   * @memberof CardCoachComponent
   */
  ngOnInit(): void {
    // this.card = this.getCard()
  }

  /**
   * Ascolta i clic fuori dalla scheda per chiudere la visualizzazione espansa.
   * 
   * @param {Event} event - Evento di clic.
   * @memberof CardCoachComponent
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
   * Gestisce l'espansione e la contrazione della scheda del coach.
   * Carica le informazioni complete del coach se la scheda viene espansa.
   * 
   * @param {Event} event - Evento di clic.
   * @memberof CardCoachComponent
   */
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

  /**
   * Gestisce la prenotazione di una chiamata con il coach selezionato.
   * Imposta l'ID del coach nella sessione e naviga verso la pagina di prenotazione.
   * 
   * @param {Event} event - Evento di clic.
   * @memberof CardCoachComponent
   */
  reservation(event: Event) {
    event.stopPropagation();
    sessionStorage.setItem('coach_id', this.card.id.toString());
    this.navigateTo('book-call');
  }

  /**
   * Ferma la propagazione dell'evento per evitare che si propaghi ulteriormente.
   * 
   * @param {Event} event - Evento di clic.
   * @memberof CardCoachComponent
   */
  stopPropagation(event: Event) {
    event.stopPropagation();
  }

  /**
   * Gestisce la navigazione verso una rotta specificata.
   * 
   * @param {string} route - La rotta verso cui navigare.
   * @memberof CardCoachComponent
   */
  navigateTo(route: string) {
    this.redirectService.setRedirect(route);
  }

}
