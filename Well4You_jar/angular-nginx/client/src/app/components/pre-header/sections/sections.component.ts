import { Component, NO_ERRORS_SCHEMA } from '@angular/core';

import { PreHeaderComponent } from '../pre-header.component';

@Component({
  selector: 'app-sections',
  standalone: true,
  imports: [PreHeaderComponent],
  schemas: [NO_ERRORS_SCHEMA],
  templateUrl: './sections.component.html',
  styleUrl: './sections.component.scss'
})
export class SectionsComponent {

  constructor(){}
    
  goBack() {
    window.history.back()
    setTimeout(() => {
      window.location.reload()
    }, 100);
  }

  isSigning(): boolean {
    console.log('current path:', window.location.pathname)
    return [
      '/sign-in',
      '/sign-in/2FA',
      '/forgot-password',
      '/reset-password',
      '/sign-up',
      '/sign-up/survey',
      '/sign-up/2FA'
    ].includes(window.location.pathname)
  }

}
