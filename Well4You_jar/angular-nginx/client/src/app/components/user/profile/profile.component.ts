import { Component, OnInit } from '@angular/core';

import { Profile } from '../../../interfaces/profile';

import { RedirectService } from '../../../services/redirect/redirect.service';
import { HttpParams } from '@angular/common/http';
import { ApiService } from '../../../services/api/api.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {

  profile!: Profile

  constructor(
    private apiService: ApiService,
    private redirectService: RedirectService
  ) {}

  async ngOnInit(): Promise<void> {
    const userId = sessionStorage.getItem('user_id');
    const params = new HttpParams().set('userId', userId!);
    try {
      const response: any = await this.apiService.get('api/user/info', params).toPromise();
      this.profile = response;
      this.profile.dob = this.profile.dob.split('T')[0]
    } catch (error) {
      console.error('Profile error', error)
    }
  }

  dobExist() {
    if (this.profile.dob)
      return true
    return false
  }

  navigateTo(route: string): void {
    this.redirectService.setRedirect(route)
  }
}
