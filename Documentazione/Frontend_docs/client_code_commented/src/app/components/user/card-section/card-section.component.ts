import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CardServiceComponent } from "../card-service/card-service.component";
import { CardSection } from '../../../interfaces/card-section';

/**
 * Componente per la visualizzazione di una sezione di carte.
 * Mostra una sezione che può contenere più carte di servizio.
 * 
 * @export
 * @class CardSectionComponent
 */
@Component({
  selector: 'app-card-section',
  standalone: true,
  imports: [CardServiceComponent, CommonModule],
  templateUrl: './card-section.component.html',
  styleUrls: ['./card-section.component.scss']
})
export class CardSectionComponent {

  /**
   * Dati della sezione da visualizzare.
   * 
   * @type {CardSection}
   * @memberof CardSectionComponent
   */
  @Input() section!: CardSection;
}
