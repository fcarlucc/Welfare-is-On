import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PurchasedSection } from '../../../../interfaces/purchased-section';

import { CardServiceComponent } from '../../card-service/card-service.component';

@Component({
  selector: 'app-purchased-section',
  standalone: true,
  imports: [CommonModule, CardServiceComponent],
  templateUrl: './purchased-section.component.html',
  styleUrl: './purchased-section.component.scss'
})
export class PurchasedSectionComponent {

  @Input() section!: PurchasedSection;

  constructor() {}

}
