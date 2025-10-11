import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpParams } from '@angular/common/http';

import { ApiService } from '../../../services/api/api.service';

@Component({
  selector: 'app-meeting-board',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './meeting-board.component.html',
  styleUrl: './meeting-board.component.scss'
})
export class MeetingBoardComponent implements OnInit {

  meetings: any

  constructor(
    private apiService: ApiService,
  ) {}

  async ngOnInit(): Promise<void> {
    const userId = sessionStorage.getItem('user_id');
    const params = new HttpParams().set('userId', userId!);

    try {
      const response: any = await this.apiService.get('api/schedule/get-bookings-user', params).toPromise()
      console.log('get meeting board success', response)
      this.meetings = response;
      console.log('dajeee' + this.meetings)
    } catch (error) {
      console.error('get meeting board error', error)
    }
  }

  isEmpty() {
    if (this.meetings[0]) {
      return false
    }
    return true
  }

}
