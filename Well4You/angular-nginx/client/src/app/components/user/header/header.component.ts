import { Component, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SidebarComponent } from './sidebar/sidebar.component';

import { RedirectService } from '../../../services/redirect/redirect.service';
import { ApiService } from '../../../services/api/api.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, SidebarComponent],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  sidebarOn: boolean = false
  profileOn: boolean = false

  constructor(
    private apiService: ApiService,
    private redirectService: RedirectService
  ) {}

  @HostListener('document:click', ['$event'])
  clickOutside(event: Event) {
    if (this.sidebarOn || this.profileOn) {
      const target = event.target as HTMLElement;
      if (!target.closest('.header')) {
        this.sidebarOn = false
        this.profileOn = false
      }
    }
  }

  toggleMenu() {
    console.log('clicked menu')
    if (this.profileOn)
      this.profileOn = false
    this.sidebarOn = !this.sidebarOn
  }

  toggleProfile() {
    console.log('clicked profile')
    if (this.sidebarOn)
      this.sidebarOn = false
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