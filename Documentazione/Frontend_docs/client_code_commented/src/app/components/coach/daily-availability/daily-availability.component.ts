import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpParams } from '@angular/common/http';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

import { DailyAvailability } from '../../../interfaces/daily-availability';
import { TimeSlot } from '../../../interfaces/time-slot';

import { ApiService } from '../../../services/api/api.service';
import { RedirectService } from '../../../services/redirect/redirect.service';
import { ValidatorService } from '../../../services/validator/validator.service';

import { ErrorMsgFormsDirective } from '../../../directives/error-msg-forms/error-msg-forms.directive';

/**
 * Componente per gestire e visualizzare la disponibilità giornaliera del coach.
 * Permette di selezionare fasce orarie disponibili e inviare la disponibilità al server.
 * @export
 * @class DailyAvailabilityComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'app-daily-availability',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, ErrorMsgFormsDirective],
  templateUrl: './daily-availability.component.html',
  styleUrls: ['./daily-availability.component.scss']
})
export class DailyAvailabilityComponent implements OnInit {

  /**
   * Modulo di disponibilità giornaliera.
   * @type {FormGroup}
   * @memberof DailyAvailabilityComponent
   */
  dailyAvailabilityForm!: FormGroup;

  /**
   * Oggetto per gestire la disponibilità giornaliera.
   * @type {DailyAvailability}
   * @memberof DailyAvailabilityComponent
   */
  dailyAvailability!: DailyAvailability;

  /**
   * Data selezionata per la disponibilità giornaliera.
   * @type {Date}
   * @memberof DailyAvailabilityComponent
   */
  day: Date = new Date();

  /**
   * Flag per mostrare o nascondere le fasce orarie.
   * @type {boolean}
   * @memberof DailyAvailabilityComponent
   */
  showTimeSlots = false;

  /**
   * Fasce orarie già impostate.
   * @type {(TimeSlot[] | undefined)}
   * @memberof DailyAvailabilityComponent
   */
  alreadySettedSlots?: TimeSlot[];

  /**
   * Fasce orarie già prenotate.
   * @type {(TimeSlot[] | undefined)}
   * @memberof DailyAvailabilityComponent
   */
  alreadyBookedSlots?: TimeSlot[];

  /**
   * Lista di fasce orarie disponibili per la selezione.
   * @type {TimeSlot[]}
   * @memberof DailyAvailabilityComponent
   */
  timeSlots: TimeSlot[] = [];

  /**
   * Fasce orarie selezionate dal coach.
   * @type {TimeSlot[]}
   * @memberof DailyAvailabilityComponent
   */
  selectedTimeSlots: TimeSlot[] = [];

  /**
   * Crea un'istanza di DailyAvailabilityComponent.
   * @param {FormBuilder} fb - Servizio per la costruzione dei moduli reattivi.
   * @param {ApiService} apiService - Servizio per le chiamate API.
   * @param {RedirectService} redirectService - Servizio per la gestione delle navigazioni.
   * @param {ValidatorService} validatorService - Servizio per la validazione dei dati.
   * @memberof DailyAvailabilityComponent
   */
  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private redirectService: RedirectService,
    private validatorService: ValidatorService
  ) {}

  /**
   * Metodo chiamato al caricamento del componente.
   * Inizializza il modulo di disponibilità giornaliera.
   * @memberof DailyAvailabilityComponent
   */
  ngOnInit(): void {
    this.dailyAvailabilityForm = this.fb.group({
      callDate: ['', [this.validatorService.validateDate()]],
      timeSlots: [[]]
    });
  }

  /**
   * Gestisce l'invio del modulo di disponibilità giornaliera.
   * Verifica se il modulo è valido e invia le fasce orarie selezionate al server.
   * @returns {Promise<void>}
   * @memberof DailyAvailabilityComponent
   */
  async onSubmit() {
    if (this.dailyAvailabilityForm.valid) {
      console.log('Selected time slots:', this.selectedTimeSlots);

      const coachId = sessionStorage.getItem('user_id');

      try {
        const response: any = await this.apiService.post('api/schedule/set-availability', {
          "coachId": coachId,
          "day": this.day.toISOString().split('T')[0],
          "slots": this.formatTimeSlotArray(this.selectedTimeSlots)
        }).toPromise();
        console.log('set availability successful', response);
        this.navigateTo("");
      } catch (error) {
        console.error('set availability error', error);
      }
    }
  }

  /**
   * Formatta un array di fasce orarie per l'invio al server.
   * @param {TimeSlot[]} timeSlots - Array di fasce orarie.
   * @returns {{ startTime: string; endTime: string }[]} - Array formattato di fasce orarie.
   * @memberof DailyAvailabilityComponent
   */
  formatTimeSlotArray(timeSlots: TimeSlot[]): { startTime: string; endTime: string }[] {
    return timeSlots.map(slot => ({
      startTime: this.formatTime(slot.startTime),
      endTime: this.formatTime(slot.endTime)
    }));
  }

  /**
   * Formatta l'orario in una stringa 'HH:MM'.
   * @param {Date} date - Data con orario da formattare.
   * @returns {string} - Orario formattato.
   * @memberof DailyAvailabilityComponent
   */
  formatTime(date: Date): string {
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    return `${hours}:${minutes}`;
  }

  /**
   * Gestisce il cambiamento della data selezionata.
   * Recupera le fasce orarie prenotate e disponibili per la data selezionata.
   * @param {Event} event - Evento di cambiamento della data.
   * @returns {Promise<void>}
   * @memberof DailyAvailabilityComponent
   */
  async onDateChange(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.value) {
      const selectedDate = new Date(input.value);
      this.day = selectedDate;
      const currentDate = new Date();
      currentDate.setHours(0, 0, 0, 0);

      if (selectedDate < currentDate) {
        this.timeSlots = [];
        this.showTimeSlots = false;
      } else {
        await this.getCompareSlots();
        this.createTimeSlots(selectedDate);
      }
    } else {
      this.timeSlots = [];
      this.showTimeSlots = false;
    }
  }

  /**
   * Recupera le fasce orarie prenotate e disponibili per la data selezionata.
   * @returns {Promise<void>}
   * @memberof DailyAvailabilityComponent
   */
  async getCompareSlots() {
    const coachId = sessionStorage.getItem('user_id');
    const params = new HttpParams().set('coachId', coachId!).set('day', this.day.toISOString().split('T')[0]);

    try {
      const response: any = await this.apiService.get('api/schedule/booked-slots', params).toPromise();
      console.log('get booked slots successful', response);
      this.alreadyBookedSlots = response;
    } catch (error) {
      console.error('Error fetching booked slots', error);
    }

    try {
      const response: any = await this.apiService.get('api/schedule/not-booked-slots', params).toPromise();
      console.log('get not booked slots successful', response);
      this.alreadySettedSlots = response;
    } catch (error) {
      console.error('Error fetching not booked slots', error);
    }
  }

  /**
   * Gestisce la selezione/deselezione di una fascia oraria.
   * @param {TimeSlot} selectedSlot - Fascia oraria selezionata.
   * @memberof DailyAvailabilityComponent
   */
  toggleSlot(selectedSlot: TimeSlot) {
    if (this.isBookedSlot(selectedSlot))
      return;

    const index = this.selectedTimeSlots.findIndex(
      slot => slot.startTime.getTime() === selectedSlot.startTime.getTime());
    if (index !== -1) {
      this.selectedTimeSlots.splice(index, 1);
    } else {
      this.selectedTimeSlots.push(selectedSlot);
    }
    this.dailyAvailabilityForm.get('timeSlots')!.setValue(this.selectedTimeSlots);
  }

  /**
   * Verifica se una fascia oraria è prenotata.
   * @param {TimeSlot} slot - Fascia oraria da verificare.
   * @returns {boolean} - Restituisce true se la fascia oraria è prenotata, altrimenti false.
   * @memberof DailyAvailabilityComponent
   */
  isBookedSlot(slot: TimeSlot): boolean {
    return this.alreadyBookedSlots?.some(s => this.isSameSlot(s, slot)) ?? false;
  }

  /**
   * Verifica se due fasce orarie sono identiche.
   * @param {TimeSlot} slot1 - Prima fascia oraria.
   * @param {TimeSlot} slot2 - Seconda fascia oraria.
   * @returns {boolean} - Restituisce true se le fasce orarie sono identiche, altrimenti false.
   * @memberof DailyAvailabilityComponent
   */
  isSameSlot(slot1: TimeSlot, slot2: TimeSlot): boolean {
    return slot1.startTime.getTime() === slot2.startTime.getTime() &&
           slot1.endTime.getTime() === slot2.endTime.getTime();
  }

  /**
   * Crea una lista di fasce orarie per un giorno specifico.
   * Esclude le fasce orarie già prenotate.
   * @param {Date} day - Data per la quale creare le fasce orarie.
   * @memberof DailyAvailabilityComponent
   */
  createTimeSlots(day: Date): void {
    console.log(this.alreadyBookedSlots);
    this.timeSlots = [];

    const startHour = 9;
    const endHour = 18;

    let startTime = new Date(day);
    startTime.setHours(startHour, 0, 0, 0);

    const endTime = new Date(day);
    endTime.setHours(endHour, 0, 0, 0);

    // Genera tutte le fasce orarie dalle 9:00 alle 17:30
    while (startTime < endTime) {
      const endTimeSlot = new Date(startTime);
      endTimeSlot.setMinutes(startTime.getMinutes() + 30);

      const newSlot: TimeSlot = {
        startTime: new Date(startTime),
        endTime: new Date(endTimeSlot)
      };

      // Verifica se la nuova fascia oraria è prenotata
      if (!this.isSlotBooked(newSlot)) {
        this.timeSlots.push(newSlot);
      }

      startTime = endTimeSlot;
    }
    this.showTimeSlots = true;
  }

  /**
   * Verifica se una fascia oraria è già prenotata.
   * @param {TimeSlot} slot - Fascia oraria da verificare.
   * @returns {boolean} - Restituisce true se la fascia oraria è prenotata, altrimenti false.
   * @memberof DailyAvailabilityComponent
   */
  isSlotBooked(slot: TimeSlot): boolean {
    return this.alreadyBookedSlots!.some(bookedSlot =>
      this.areSlotsEqual(slot, bookedSlot)
    );
  }

  /**
   * Confronta due fasce orarie per verificarne l'uguaglianza.
   * @param {TimeSlot} slot1 - Prima fascia oraria.
   * @param {TimeSlot} slot2 - Seconda fascia oraria.
   * @returns {boolean} - Restituisce true se le fasce orarie sono uguali, altrimenti false.
   * @memberof DailyAvailabilityComponent
   */
  areSlotsEqual(slot1: TimeSlot, slot2: TimeSlot): boolean {
    return this.formatTime2(slot1.startTime) === slot2.startTime.toString() &&
           this.formatTime2(slot1.endTime) === slot2.endTime.toString();
  }

  /**
   * Formatta l'orario in una stringa 'HH:MM:SS'.
   * @param {Date} date - Data con orario da formattare.
   * @returns {string} - Orario formattato.
   * @memberof DailyAvailabilityComponent
   */
  formatTime2(date: Date): string {
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    const seconds = date.getSeconds().toString().padStart(2, '0');

    return `${hours}:${minutes}:${seconds}`;
  }

  /**
   * Gestisce la navigazione verso un'altra rotta.
   * @param {string} route - La rotta verso cui navigare.
   * @memberof DailyAvailabilityComponent
   */
  navigateTo(route: string): void {
    this.redirectService.setRedirect(route);
  }
}
