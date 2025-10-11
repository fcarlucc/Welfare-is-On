import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Location } from '../../interfaces/location';
import { ApiService } from '../api/api.service';

/**
 * Servizio per la gestione delle operazioni relative alle mappe,
 * come ottenere coordinate geografiche da un indirizzo o viceversa,
 * e recuperare la posizione dell'utente.
 */
@Injectable({
  providedIn: 'root'
})
export class MapsService {
  private apiKey: string = 'AIzaSyCvVL7YIs_8tSfnVfpFUiv64XPZ1GE8ADg';
  private geocodeUrl = 'https://maps.googleapis.com/maps/api/geocode/json';
  location!: Location;

  /**
   * Crea un'istanza del servizio `MapsService`.
   * @param apiService - Il servizio per le chiamate API.
   */
  constructor(
    private apiService: ApiService
  ) { }

  /**
   * Recupera le coordinate geografiche (latitudine e longitudine) per un indirizzo fornito.
   * Utilizza l'API di Google Geocoding per ottenere le coordinate.
   * @param address - L'indirizzo per il quale ottenere le coordinate.
   * @returns Una `Promise` che risolve in un oggetto contenente latitudine e longitudine.
   * @throws Error se la richiesta alle API fallisce.
   */
  async getLatLong(address: string): Promise<any> {
    const url = `${this.geocodeUrl}?address=${encodeURIComponent(address)}&key=${this.apiKey}`;
    try {
      const response = await this.apiService.get2(url);
      console.log('Response from getLatLong:', response.results[0].geometry.location);
      return response.results[0].geometry.location;
    } catch (error) {
      console.error('Error in getLatLong:', error);
      throw error;
    }
  }

  /**
   * Recupera l'indirizzo formattato per una coppia di coordinate geografiche.
   * Utilizza l'API di Google Geocoding per ottenere l'indirizzo.
   * @param lat - La latitudine della posizione.
   * @param lng - La longitudine della posizione.
   * @returns Una `Promise` che risolve in un oggetto contenente l'indirizzo formattato.
   * @throws Error se la richiesta alle API fallisce.
   */
  async getAddress(lat: number, lng: number): Promise<any> {
    const url = `${this.geocodeUrl}?latlng=${lat},${lng}&key=${this.apiKey}`;
    try {
      const response = await this.apiService.get2(url);
      console.log('Response from getAddress:', response.results[0].formatted_address);
      return response;
    } catch (error) {
      console.error('Error in getAddress:', error);
      throw error;
    }
  }

  /**
   * Recupera la posizione attuale dell'utente utilizzando l'API di geolocalizzazione del browser.
   * @returns Una `Promise` che risolve in un oggetto `Location` con le coordinate dell'utente.
   * @throws Error se la geolocalizzazione fallisce o non è supportata.
   */
  getUserLocation(): Promise<Location> {
    return new Promise((resolve, reject) => {
      if (!navigator.geolocation) {
        console.log('Location is not supported');
        this.location = { latitude: null, longitude: null };
        resolve(this.location);
      } else {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            this.location = {
              latitude: position.coords.latitude,
              longitude: position.coords.longitude
            };
            console.log('longitude:', this.location.longitude, 'latitude:', this.location.latitude);
            resolve(this.location);
          },
          (error) => {
            console.log('Error getting location:', error);
            this.location = { latitude: null, longitude: null };
            reject(error);
          }
        );
      }
    });
  }

  /**
   * Recupera la posizione attuale dell'utente e invia i dati al server.
   * Utilizza l'API di geolocalizzazione del browser e invia i dati al server tramite il servizio `ApiService`.
   * @param email - L'email dell'utente a cui associare la posizione.
   */
  getUserPosition(email: string) {
    if (!navigator.geolocation) {
      console.log('Location is not supported');
      this.location = { latitude: null, longitude: null };
    } else {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          this.location = {
            latitude: position.coords.latitude,
            longitude: position.coords.longitude
          };
          console.log('longitude:', this.location.longitude, 'latitude:', this.location.latitude);
          this.sendLocation(email, this.location.latitude!, this.location.longitude!);
        },
        (error) => {
          console.log('Error getting location:', error);
          this.location = { latitude: null, longitude: null };
        }
      );
    }
  }

  /**
   * Invia la posizione dell'utente al server.
   * Utilizza il servizio `ApiService` per inviare i dati al server.
   * @param email - L'email dell'utente a cui associare la posizione.
   * @param latitude - La latitudine della posizione dell'utente.
   * @param longitude - La longitudine della posizione dell'utente.
   */
  private sendLocation(email: string, latitude: number, longitude: number) {
    console.log('lat:', latitude, 'lon:', longitude);
    this.apiService.post('api/user/user-location',
      {
        email: email,
        latitude: latitude,
        longitude: longitude
      }
    ).subscribe({
      next: (response) => {
        console.log('Location :', response);
      },
      error: (error) => {
        console.error('Location error', error);
      }
    });
  }
}


  // getLatLong(address: string): Observable<any> {
  //   console.log('get latlong entered')
  //   const url = `${this.geocodeUrl}?address=${encodeURIComponent(address)}&key=${this.apiKey}`;
  //   console.log('getLatLong', url)
  //   return this.apiService.get2(url)
  // }

  // getAddress(lat: number, lng: number): Observable<any> {
  //   console.log('get address entered')
  //   const url = `${this.geocodeUrl}?latlng=${lat},${lng}&key=${this.apiKey}`;
  //   console.log('getAddress', url)
  //   return this.apiService.get2(url).pipe(
  //     tap(response => {
  //       console.log('Response from getAddress:', response);
  //     })
  //   );
  // }

  // getUserPosition(email: string): Promise<Location> {
  //   return new Promise((resolve, reject) => {
  //     if (!navigator.geolocation) {
  //       console.log('Location is not supported')
  //       this.location.latitude = null
  //       this.location.longitude = null
  //       resolve(this.location)
  //     } else {
  //       navigator.geolocation.getCurrentPosition(
  //         (position) => {
  //           this.location.latitude = position.coords.latitude
  //           this.location.longitude = position.coords.longitude
  //           console.log('longitude:', this.location.longitude, 'latitude:', this.location.latitude)
  //           this.sendLocation(email, this.location.latitude, this.location.longitude)
  //           resolve(this.location);
  //         },
  //         (error) => {
  //           console.error('Error getting location', error)
  //           reject(error)
  //         }
  //       );
  //     }
  //   });
  // }

  // getLatitude(): number | null {
  //   if (this.location)
  //     return this.location.latitude
  //   return null
  // }

  // getLongitude(): number | null {
  //   if (this.location)
  //     return this.location.longitude
  //   return null
  // }

  // async getLatitude(): Promise<number | null> {
  //   try {
  //     const location = await this.getUserPosition()
  //     return location.latitude
  //   } catch (error) {
  //     console.error('Error getting latitude', error)
  //     return null
  //   }
  // }

  // async getLongitude(): Promise<number | null> {
  //   try {
  //     const location = await this.getUserPosition()
  //     return location.longitude
  //   } catch (error) {
  //     console.error('Error getting longitude', error)
  //     return null
  //   }
  // }
