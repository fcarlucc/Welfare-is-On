import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpParams } from '@angular/common/http';

import { ApiService } from '../../../services/api/api.service';

/**
 * Componente per visualizzare la bacheca delle prenotazioni di un coach.
 * Recupera le prenotazioni tramite un servizio API e le visualizza nel template.
 * @export
 * @class BookingBoardComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-booking-board',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './booking-board.component.html',
  styleUrls: ['./booking-board.component.scss']
})
export class BookingBoardComponent implements OnInit {

  /**
   * Lista delle prenotazioni del coach.
   * @type {any}
   * @memberof BookingBoardComponent
   */
  bookings: any;

  /**
   * Crea un'istanza di BookingBoardComponent.
   * @param {ApiService} apiService - Servizio per le chiamate API.
   * @memberof BookingBoardComponent
   */
  constructor(
    private apiService: ApiService,
  ) {}

  /**
   * Metodo iniziale chiamato al caricamento del componente.
   * Recupera le prenotazioni tramite il servizio API e le assegna alla variabile `bookings`.
   * @returns {Promise<void>}
   * @memberof BookingBoardComponent
   */
  async ngOnInit(): Promise<void> {
    // Recupera l'ID del coach dalla sessione
    const coachId = sessionStorage.getItem('user_id');

    // Imposta i parametri per la richiesta API
    const params = new HttpParams().set('coachId', coachId!);

    try {
      // Effettua una richiesta GET per ottenere le prenotazioni
      const response: any = await this.apiService.get('api/schedule/get-bookings', params).toPromise();
      console.log('get booking board success', response);
      this.bookings = response;
    } catch (error) {
      // Gestisce eventuali errori durante la richiesta
      console.error('get booking board error', error);
    }
  }

  /**
   * Verifica se la lista delle prenotazioni è vuota.
   * @returns {boolean} - Restituisce true se la lista è vuota, altrimenti false.
   * @memberof BookingBoardComponent
   */
  isEmpty(): boolean {
    return !this.bookings || this.bookings.length === 0;
  }
}
