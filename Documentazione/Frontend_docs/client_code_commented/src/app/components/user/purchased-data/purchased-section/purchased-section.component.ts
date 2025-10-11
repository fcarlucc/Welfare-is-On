import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PurchasedSection } from '../../../../interfaces/purchased-section';
import { CardServiceComponent } from '../../card-service/card-service.component';

/**
 * Componente per la visualizzazione di una sezione di servizi acquistati.
 * 
 * @export
 * @class PurchasedSectionComponent
 */
@Component({
  selector: 'app-purchased-section',
  standalone: true,
  imports: [CommonModule, CardServiceComponent],
  templateUrl: './purchased-section.component.html',
  styleUrls: ['./purchased-section.component.scss']
})
export class PurchasedSectionComponent {

  /**
   * Sezione di servizi acquistati da visualizzare.
   * 
   * @type {PurchasedSection}
   * @memberof PurchasedSectionComponent
   */
  @Input() section!: PurchasedSection;

  /**
   * Crea un'istanza di PurchasedSectionComponent.
   * 
   * @memberof PurchasedSectionComponent
   */
  constructor() {}

}
