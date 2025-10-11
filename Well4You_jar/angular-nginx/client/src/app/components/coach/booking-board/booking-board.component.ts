import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpParams } from '@angular/common/http';

import { ApiService } from '../../../services/api/api.service';

@Component({
  selector: 'app-booking-board',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './booking-board.component.html',
  styleUrl: './booking-board.component.scss'
})
export class BookingBoardComponent implements OnInit {

  bookings: any

  constructor(
    private apiService: ApiService,
  ) {}

  async ngOnInit(): Promise<void> {
    const coachId = sessionStorage.getItem('user_id');
    const params = new HttpParams().set('coachId', coachId!);

    try {
      const response: any = await this.apiService.get('api/schedule/get-bookings', params).toPromise()
      console.log('get booking board success', response)
      this.bookings = response;
    } catch (error) {
      console.error('get booking board error', error)
    }
  }

  isEmpty() {
    if (this.bookings[0])
      return false
    return true
  }
}
