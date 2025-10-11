import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RedirectService } from '../../../../services/redirect/redirect.service';

@Component({
  selector: 'app-sidebar-coach',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {
  sectionOn: boolean = false
  pillarOn: boolean = false

  constructor(
    private redirectService: RedirectService
  ) {}

  toggleSection(event: Event) {
    event.stopPropagation();
    this.sectionOn = !this.sectionOn
    this.pillarOn = !this.pillarOn
  }

  navigateTo(route: string): void {
    this.redirectService.setRedirect(route)
  }

}
