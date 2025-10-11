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

@Component({
  selector: 'app-daily-availability',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, ErrorMsgFormsDirective],
  templateUrl: './daily-availability.component.html',
  styleUrl: './daily-availability.component.scss'
})
export class DailyAvailabilityComponent implements OnInit {

  dailyAvailabilityForm!: FormGroup
  dailyAvailability!: DailyAvailability
  day: Date = new Date();
  showTimeSlots = false
  alreadySettedSlots?: TimeSlot[]
  alreadyBookedSlots?: TimeSlot[]
  timeSlots: TimeSlot[] = []
  selectedTimeSlots: TimeSlot[] = []

  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private redirectService: RedirectService,
    private validatorService: ValidatorService
  ) {}

  ngOnInit(): void {
    this.dailyAvailabilityForm = this.fb.group({
      callDate: ['', [this.validatorService.validateDate()]],
      timeSlots: [[]]
    });
  }

  async onSubmit() {
    if (this.dailyAvailabilityForm.valid) {
      console.log('Selected time slots:', this.selectedTimeSlots)

      const coachId = sessionStorage.getItem('user_id')

      try {
        const response: any = await this.apiService.post('api/schedule/set-availability', {
          "coachId" : coachId,
          "day" : this.day.toISOString().split('T')[0],
          "slots" : this.formatTimeSlotArray(this.selectedTimeSlots)
        }).toPromise()
        console.log('set availability successful', response)
        this.navigateTo("")
      } catch (error) {
        console.error('set availability error', error)
      }

    }
  }

  formatTimeSlotArray(timeSlots: TimeSlot[]): { startTime: string; endTime: string }[] {
    return timeSlots.map(slot => ({
      startTime: this.formatTime(slot.startTime),
      endTime: this.formatTime(slot.endTime)
    }));
  }

  formatTime(date: Date): string {
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    return `${hours}:${minutes}`;
  }

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

  toggleSlot(selectedSlot: TimeSlot) {
    if (this.isBookedSlot(selectedSlot))
      return

    const index = this.selectedTimeSlots.findIndex(
      slot => slot.startTime.getTime() === selectedSlot.startTime.getTime());
    if (index !== -1) {
      this.selectedTimeSlots.splice(index, 1)
    } else {
      this.selectedTimeSlots.push(selectedSlot)
    }
    this.dailyAvailabilityForm.get('timeSlots')!.setValue(this.selectedTimeSlots)
  }

  isSelected(slot: TimeSlot) {
    // return this.selectedSlots.some(s => this.isSameSlot(s, slot));
  }

  isBookedSlot(slot: TimeSlot): boolean {
    return this.alreadyBookedSlots?.some(s => this.isSameSlot(s, slot)) ?? false;
  }

  isSameSlot(slot1: TimeSlot, slot2: TimeSlot): boolean {
    return slot1.startTime === slot2.startTime && slot1.endTime === slot2.endTime;
  }

  // isSelected(slot: TimeSlot): boolean {
  //   return this.selectedTimeSlots.some(
  //     ts => ts.startTime.getTime() === slot.startTime.getTime());
  // }

  createTimeSlots(day: Date): void {
    console.log(this.alreadyBookedSlots)
    this.timeSlots = [];

    const startHour = 9;
    const endHour = 18;

    let startTime = new Date(day);
    startTime.setHours(startHour, 0, 0, 0);

    const endTime = new Date(day);
    endTime.setHours(endHour, 0, 0, 0);

    // Generate all slots from 9:00 to 17:30
    while (startTime < endTime) {
      const endTimeSlot = new Date(startTime);
      endTimeSlot.setMinutes(startTime.getMinutes() + 30);

      const newSlot: TimeSlot = {
        startTime: new Date(startTime),
        endTime: new Date(endTimeSlot)
      };

      // Check if the new slot overlaps with any already booked slot
      if (!this.isSlotBooked(newSlot)) {
        this.timeSlots.push(newSlot);
      }

      startTime = endTimeSlot;
    }
    this.showTimeSlots = true
  }

  isSlotBooked(slot: TimeSlot): boolean {
    return this.alreadyBookedSlots!.some(bookedSlot =>
      this.areSlotsEqual(slot, bookedSlot)
    );
  }

  areSlotsEqual(slot1: TimeSlot, slot2: TimeSlot): boolean {
    return this.formatTime2(slot1.startTime) === slot2.startTime.toString() &&
           this.formatTime2(slot1.endTime) === slot2.endTime.toString()
  }

  formatTime2(date: Date): string {
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    const seconds = date.getSeconds().toString().padStart(2, '0');

    return `${hours}:${minutes}:${seconds}`;
  }

  navigateTo(route: string): void {
    this.redirectService.setRedirect(route)
  }
}