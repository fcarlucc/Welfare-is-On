import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

import { BookCall } from '../../../interfaces/book-call';
import { TimeSlot } from '../../../interfaces/time-slot';

import { ApiService } from '../../../services/api/api.service';
import { RedirectService } from '../../../services/redirect/redirect.service';
import { ValidatorService } from '../../../services/validator/validator.service';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-book-call',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './book-call.component.html',
  styleUrl: './book-call.component.scss'
})
export class BookCallComponent implements OnInit {

  bookCallForm!: FormGroup
  bookCall: BookCall = { day: new Date(), slot: { startTime: new Date(), endTime: new Date() } }; // Initialize with default values
  days!: Date[]
  slots!: TimeSlot[]
  showSelect: boolean = false
  selectedDay: Date | null = null;


  constructor(
    private fb: FormBuilder,
    private apiService: ApiService,
    private redirectService: RedirectService,
    private validatorService: ValidatorService
  ) {}

  async ngOnInit(): Promise<void> {
    this.bookCallForm = this.fb.group({
      days: ['', [this.validatorService.requiredField()]],
      slot: ['', this.validatorService.requiredField()],
      notes: ['', ]
    });

    const coachId = sessionStorage.getItem('coach_id') //come prendo il coach id
    const params = new HttpParams().set('coachId', coachId!)

    try {
      const response : any = await this.apiService.get('api/schedule/get-available-days-coach', params).toPromise()
      console.log('get days successful', response)
      this.days = response
      console.log(this.days)
    } catch (error) {
      console.error('Showcase coach error', error)
    }
  }

  getDayMonth(dateString: string): string {
    const [year, month, day] = dateString.split('-');
    return `${day}/${month}`;
  }

  async toggleDay(day: Date) {
    if (this.selectedDay === day)
      this.selectedDay = null
    else
      this.selectedDay = day
    this.showSelect = !!this.selectedDay
    console.log('test')

    const coachId = sessionStorage.getItem('coach_id') //come prendo il coach id
    const params = new HttpParams().set('coachId', coachId!).set('day', day.toString())

    try {
      const response: any = await this.apiService.get('api/schedule/not-booked-slots', params).toPromise()
      console.log('get days successful', response)
      this.slots = response
    } catch (error) {
      console.error('Showcase coach error', error)
    }
    this.bookCall.day = day
  }

  isSelected(day: Date): boolean {
    return this.selectedDay === day
  }

  onSlotChange(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    const selectedIndex = selectElement.selectedIndex;
    const selectedSlot = this.slots[selectedIndex - 1]; // Subtract 1 to account for the default option
    this.bookCall.slot = selectedSlot
    console.log('start time = ' + this.bookCall.slot.startTime)
    console.log('end time = ' + this.bookCall.slot.endTime)
  }

  async onSubmit() {
    // if (this.bookCallForm.valid) {
      this.bookCall.coachId = +sessionStorage.getItem('coach_id')!
      this.bookCall.userId = +sessionStorage.getItem('user_id')!
      this.bookCall.text = this.bookCallForm.value.notes

      try {
        await this.apiService.post('api/schedule/set-booking', {
          "coachId" : this.bookCall.coachId,
          "userId" : this.bookCall.userId,
          "notes" : this.bookCall.text,
          "day" : this.bookCall.day!,
          "slot" : {
            "startTime" : this.bookCall.slot?.startTime,
            "endTime" : this.bookCall.slot?.endTime
          }

        }).toPromise()
        this.navigateTo("")
        console.log('set booking successful')
      } catch (error) {
        console.error('set booking error', error)
      }
    // }
  }

  navigateTo(route: string): void {
    this.redirectService.setRedirect(route)
  }
}
