import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CoachSection } from '../../../interfaces/coach-section';

import { CardCoachComponent } from '../card-coach/card-coach.component';

@Component({
  selector: 'app-coach-section',
  standalone: true,
  imports: [CommonModule, CardCoachComponent],
  templateUrl: './coach-section.component.html',
  styleUrl: './coach-section.component.scss'
})
export class CoachSectionComponent {

  @Input() section!: CoachSection;

  constructor() {}

}
