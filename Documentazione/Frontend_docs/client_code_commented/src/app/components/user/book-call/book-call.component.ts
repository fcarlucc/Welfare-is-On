import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

import { BookCall } from '../../../interfaces/book-call';
import { TimeSlot } from '../../../interfaces/time-slot';

import { ApiService } from '../../../services/api/api.service';
import { RedirectService } from '../../../services/redirect/redirect.service';
import { ValidatorService } from '../../../services/validator/validator.service';
import { HttpParams } from '@angular/common/http';

/**
 * Componente per la prenotazione di chiamate con un coach.
 * Gestisce la visualizzazione dei giorni disponibili e degli slot orari, e l'invio della prenotazione.
 * 
 * @export
 * @class BookCallComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-book-call',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './book-call.component.html',
  styleUrls: ['./book-call.component.scss']
})
export class BookCallComponent implements OnInit {

  bookCallForm!: FormGroup; // Modulo di prenotazione della chiamata
  bookCall: BookCall = { day: new Date(), slot: { startTime: new Date(), endTime: new Date() } }; // Prenotazione di chiamata inizializzata con valori predefiniti
  days!: Date[]; // Giorni disponibili per la prenotazione
  slots!: TimeSlot[]; // Slot orari disponibili per la prenotazione
  showSelect: boolean = false; // Flag per mostrare la selezione del giorno
  selectedDay: Date | null = null; // Giorno selezionato per la prenotazione

  /**
   * Crea un'istanza di BookCallComponent.
   * 
   * @param {FormBuilder} fb - Servizio per la costruzione dei moduli reattivi.
   * @param {ApiService} apiService - Servizio per le chiamate API.
   * @param {RedirectService} redirectService - Servizio per la gestione della navigazione.
   * @param {ValidatorService} validatorService - Servizio per la validazione dei moduli.
   */
  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private redirectService: RedirectService,
    private validatorService: ValidatorService
  ) {}

  /**
   * Metodo chiamato all'inizializzazione del componente.
   * Configura il modulo di prenotazione e recupera i giorni disponibili dal server.
   * 
   * @memberof BookCallComponent
   */
  async ngOnInit(): Promise<void> {
    // Inizializza il modulo di prenotazione con validazione
    this.bookCallForm = this.fb.group({
      days: ['', [this.validatorService.requiredField()]],
      slot: ['', this.validatorService.requiredField()],
      notes: ['']
    });

    const coachId = sessionStorage.getItem('coach_id'); // Recupera l'ID del coach dalla sessione
    const params = new HttpParams().set('coachId', coachId!);

    try {
      // Recupera i giorni disponibili per il coach
      const response: any = await this.apiService.get('api/schedule/get-available-days-coach', params).toPromise();
      console.log('get days successful', response);
      this.days = response;
      console.log(this.days);
    } catch (error) {
      console.error('Showcase coach error', error);
    }
  }

  /**
   * Converte una data in formato stringa "YYYY-MM-DD" nel formato "DD/MM".
   * 
   * @param {string} dateString - Data in formato stringa "YYYY-MM-DD".
   * @returns {string} - Data convertita nel formato "DD/MM".
   * @memberof BookCallComponent
   */
  getDayMonth(dateString: string): string {
    const [year, month, day] = dateString.split('-');
    return `${day}/${month}`;
  }

  /**
   * Gestisce la selezione e la visualizzazione degli slot orari per un giorno selezionato.
   * 
   * @param {Date} day - Giorno da selezionare.
   * @memberof BookCallComponent
   */
  async toggleDay(day: Date) {
    this.selectedDay = this.selectedDay === day ? null : day;
    this.showSelect = !!this.selectedDay;
    console.log('test');

    const coachId = sessionStorage.getItem('coach_id'); // Recupera l'ID del coach dalla sessione
    const params = new HttpParams().set('coachId', coachId!).set('day', day.toString());

    try {
      // Recupera gli slot orari non prenotati per il giorno selezionato
      const response: any = await this.apiService.get('api/schedule/not-booked-slots', params).toPromise();
      console.log('get days successful', response);
      this.slots = response;
    } catch (error) {
      console.error('Showcase coach error', error);
    }
    this.bookCall.day = day;
  }

  /**
   * Verifica se un giorno è selezionato.
   * 
   * @param {Date} day - Giorno da verificare.
   * @returns {boolean} - Indica se il giorno è selezionato.
   * @memberof BookCallComponent
   */
  isSelected(day: Date): boolean {
    return this.selectedDay === day;
  }

  /**
   * Gestisce la selezione di uno slot orario per la prenotazione.
   * 
   * @param {TimeSlot} slot - Slot orario da selezionare.
   * @memberof BookCallComponent
   */
  toggleSlot(slot: TimeSlot) {
    this.bookCall.slot = slot;
  }

  /**
   * Gestisce l'invio della prenotazione di chiamata.
   * Invia i dati di prenotazione al server e reindirizza l'utente.
   * 
   * @memberof BookCallComponent
   */
  async onSubmit() {
    // if (this.bookCallForm.valid) {
    this.bookCall.coachId = +sessionStorage.getItem('coach_id')!;
    this.bookCall.userId = +sessionStorage.getItem('user_id')!;
    this.bookCall.text = this.bookCallForm.value.notes;

    try {
      // Invia i dati di prenotazione al server
      await this.apiService.post('api/schedule/set-booking', {
        "coachId": this.bookCall.coachId,
        "userId": this.bookCall.userId,
        "notes": this.bookCall.text,
        "day": this.bookCall.day!,
        "slot": {
          "startTime": this.bookCall.slot?.startTime,
          "endTime": this.bookCall.slot?.endTime
        }
      }).toPromise();
      this.navigateTo("");
      console.log('set booking successful');
    } catch (error) {
      console.error('set booking error', error);
    }
    // }
  }

  /**
   * Gestisce la navigazione verso una rotta specificata.
   * 
   * @param {string} route - La rotta verso cui navigare.
   * @memberof BookCallComponent
   */
  navigateTo(route: string): void {
    this.redirectService.setRedirect(route);
  }
}
