import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RedirectService } from '../../../../services/redirect/redirect.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
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
