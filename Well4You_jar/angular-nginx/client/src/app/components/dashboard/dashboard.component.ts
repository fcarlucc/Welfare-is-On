import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  ngOnInit(): void {
  }

  typeDynamicText(): void {
    const textElement = document.getElementById('dynamic-text')
    const text = "Benvenuti nel Portale del Welfare Aziendale"
    let index = 0

    if (textElement) {
      function typeText() {
        if (index < text.length) {
          textElement!.innerHTML += text.charAt(index)
          index++
          setTimeout(typeText, 100)
        }
      }
      typeText()
    }
  }
}



  // @ViewChild('mapContainer', { static: false }) mapContainer?: ElementRef;

  // map?: google.maps.Map;

  // constructor() { }

  // ngOnInit(): void {
  //   this.loadGoogleMapsScript().then(() => {
  //     this.initMap();
  //   });
  // }

  // loadGoogleMapsScript(): Promise<void> {
  //   return new Promise((resolve, reject) => {
  //     if (document.getElementById('google-maps-script')) {
  //       resolve();
  //       return;
  //     }

  //     const script = document.createElement('script');
  //     script.id = 'google-maps-script';
  //     script.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyCvVL7YIs_8tSfnVfpFUiv64XPZ1GE8ADg&libraries=places';
  //     script.onload = () => resolve();
  //     script.onerror = (error: any) => reject(error);
  //     document.body.appendChild(script);
  //   });
  // }

  // async initMap(): Promise<void> {
  //   const position = { lat: -25.344, lng: 131.031 };

  //   // Richiesta delle librerie necessarie.
  //   const { Map } = (await google.maps.importLibrary('maps')) as google.maps.MapsLibrary;
  //   const { AdvancedMarkerElement } = (await google.maps.importLibrary('marker')) as google.maps.MarkerLibrary;

  //   // Creazione della mappa centrata su Uluru
  //   this.map = new Map(this.mapContainer?.nativeElement, {
  //     zoom: 4,
  //     center: position,
  //     mapId: 'DEMO_MAP_ID',
  //   });

  //   // Creazione del marker posizionato su Uluru
  //   new AdvancedMarkerElement({
  //     map: this.map,
  //     position: position,
  //     title: 'Uluru'
  //   });
  // }
