import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ShowCaseSection } from '../../../../interfaces/show-case-section';
import { CardServiceComponent } from '../../card-service/card-service.component';

/**
 * Componente per la visualizzazione di una sezione di showcase.
 * 
 * @export
 * @class ShowCaseSectionComponent
 */
@Component({
  selector: 'app-show-case-section',
  standalone: true,
  imports: [CommonModule, CardServiceComponent],
  templateUrl: './show-case-section.component.html',
  styleUrls: ['./show-case-section.component.scss']
})
export class ShowCaseSectionComponent {

  /**
   * Dati della sezione di showcase da visualizzare.
   * 
   * @type {ShowCaseSection}
   * @memberof ShowCaseSectionComponent
   */
  @Input() section!: ShowCaseSection;

  /**
   * Crea un'istanza di ShowCaseSectionComponent.
   * 
   * @memberof ShowCaseSectionComponent
   */
  constructor() {}
}
