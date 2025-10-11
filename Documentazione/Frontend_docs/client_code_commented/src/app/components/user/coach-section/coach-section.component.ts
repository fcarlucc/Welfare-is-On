import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CoachSection } from '../../../interfaces/coach-section';
import { CardCoachComponent } from '../card-coach/card-coach.component';

/**
 * Componente per la visualizzazione di una sezione di allenatori.
 * Questo componente visualizza una sezione che contiene una lista di allenatori
 * utilizzando il componente `CardCoachComponent` per ogni allenatore.
 * 
 * @export
 * @class CoachSectionComponent
 */
@Component({
  selector: 'app-coach-section',
  standalone: true,
  imports: [CommonModule, CardCoachComponent],
  templateUrl: './coach-section.component.html',
  styleUrls: ['./coach-section.component.scss']
})
export class CoachSectionComponent {

  /**
   * Sezione di allenatori da visualizzare.
   * 
   * @type {CoachSection}
   * @memberof CoachSectionComponent
   */
  @Input() section!: CoachSection;

  /**
   * Crea un'istanza di CoachSectionComponent.
   * 
   * @memberof CoachSectionComponent
   */
  constructor() {}

}
