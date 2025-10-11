import { Component, Input, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

/**
 * Componente per la visualizzazione di una mappa con coordinate specificate.
 * 
 * @export
 * @class MapComponent
 */
@Component({
  selector: 'app-map',
  standalone: true,
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  imports: [],
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent {

  /**
   * Coordinate geografiche da visualizzare sulla mappa.
   * 
   * @type {{ lat: number, lng: number }}
   * @memberof MapComponent
   */
  @Input() coordinates!: { lat: number, lng: number };

}
