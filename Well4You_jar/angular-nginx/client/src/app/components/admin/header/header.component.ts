import { Component } from '@angular/core';

import { RedirectService } from '../../../services/redirect/redirect.service';
import { ApiService } from '../../../services/api/api.service';

@Component({
  selector: 'app-header-admin',
  standalone: true,
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderAdminComponent {

  profileOn: boolean = false

  constructor(
    private apiService: ApiService,
    private redirectService: RedirectService
  ) {}

  toggleProfile() {
    console.log('clicked profile')
    this.profileOn = !this.profileOn
  }

  signOut() {
    try {
      this.apiService.post('api/auth/logout', {}).toPromise();
      sessionStorage.removeItem('access_token')
      sessionStorage.removeItem('user_id')
      sessionStorage.removeItem('email')
      sessionStorage.removeItem('full_name')
      sessionStorage.removeItem('role')
    } catch (error) {
      console.error('logout error', error);
    }
  }

  navigateTo(route: string): void {
    this.redirectService.setRedirect(route)
  }
}
