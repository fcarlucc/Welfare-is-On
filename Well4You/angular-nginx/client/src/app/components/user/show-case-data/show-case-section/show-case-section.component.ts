import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ShowCaseSection } from '../../../../interfaces/show-case-section';

import { CardServiceComponent } from '../../card-service/card-service.component';

@Component({
  selector: 'app-show-case-section',
  standalone: true,
  imports: [CommonModule, CardServiceComponent],
  templateUrl: './show-case-section.component.html',
  styleUrl: './show-case-section.component.scss'
})
export class ShowCaseSectionComponent {

  @Input() section!: ShowCaseSection;

  constructor() {}

}
