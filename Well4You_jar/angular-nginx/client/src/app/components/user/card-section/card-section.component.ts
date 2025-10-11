import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CardServiceComponent } from "../card-service/card-service.component";
import { CardSection } from '../../../interfaces/card-section';

@Component({
  selector: 'app-card-section',
  standalone: true,
  imports: [CardServiceComponent, CommonModule],
  templateUrl: './card-section.component.html',
  styleUrl: './card-section.component.scss'
})
export class CardSectionComponent {

  @Input() section!: CardSection;
}
