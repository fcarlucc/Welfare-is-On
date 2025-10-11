import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpParams } from '@angular/common/http';

import { ApiService } from '../../../services/api/api.service';

/**
 * Componente per la visualizzazione del cruscotto delle riunioni dell'utente.
 * 
 * @export
 * @class MeetingBoardComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-meeting-board',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './meeting-board.component.html',
  styleUrls: ['./meeting-board.component.scss']
})
export class MeetingBoardComponent implements OnInit {

  /**
   * Lista delle riunioni dell'utente.
   * 
   * @type {*}
   * @memberof MeetingBoardComponent
   */
  meetings: any;

  /**
   * Crea un'istanza di MeetingBoardComponent.
   * 
   * @param {ApiService} apiService - Servizio per le chiamate API.
   */
  constructor(
    private apiService: ApiService,
  ) {}

  /**
   * Metodo chiamato all'inizializzazione del componente.
   * Recupera le riunioni dell'utente dal server e le assegna alla variabile `meetings`.
   * 
   * @memberof MeetingBoardComponent
   */
  async ngOnInit(): Promise<void> {
    const userId = sessionStorage.getItem('user_id');
    const params = new HttpParams().set('userId', userId!);

    try {
      const response: any = await this.apiService.get('api/schedule/get-bookings-user', params).toPromise();
      console.log('get meeting board success', response);
      this.meetings = response;
    } catch (error) {
      console.error('get meeting board error', error);
    }
  }

  /**
   * Verifica se la lista delle riunioni è vuota.
   * 
   * @returns {boolean} - Restituisce `true` se la lista è vuota, altrimenti `false`.
   * @memberof MeetingBoardComponent
   */
  isEmpty(): boolean {
    return !this.meetings || this.meetings.length === 0;
  }
}
