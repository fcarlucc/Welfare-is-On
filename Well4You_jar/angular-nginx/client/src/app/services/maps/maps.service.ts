import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

import { Location } from '../../interfaces/location';

import { ApiService } from '../api/api.service';

@Injectable({
  providedIn: 'root'
})
export class MapsService {
  private apiKey: string = 'AIzaSyCvVL7YIs_8tSfnVfpFUiv64XPZ1GE8ADg'
  private geocodeUrl = 'https://maps.googleapis.com/maps/api/geocode/json';
  location!: Location

  constructor(
    private apiService: ApiService
  ) { }

  async getLatLong(address: string): Promise<any> {
    const url = `${this.geocodeUrl}?address=${encodeURIComponent(address)}&key=${this.apiKey}`
    try {
      const response = await this.apiService.get2(url)
      console.log('Response from getLatLong:', response.results[0].geometry.location)
      return response.results[0].geometry.location
    } catch (error) {
      console.error('Error in getLatLong:', error)
      throw error;
    }
  }

  async getAddress(lat: number, lng: number): Promise<any> {
    const url = `${this.geocodeUrl}?latlng=${lat},${lng}&key=${this.apiKey}`
    try {
      const response = await this.apiService.get2(url)
      console.log('Response from getAddress:', response.results[0].formatted_address)
      return response
    } catch (error) {
      console.error('Error in getAddress:', error)
      throw error
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

  getUserPosition(email: string) {
    if (!navigator.geolocation) {
      console.log('Location is not supported')
      this.location = { latitude: null, longitude: null }
    } else {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          this.location = {
            latitude: position.coords.latitude,
            longitude: position.coords.longitude
          };
          console.log('longitude:', this.location.longitude, 'latitude:', this.location.latitude)
          this.sendLocation(email, this.location.latitude!, this.location.longitude!)
        },
        (error) => {
          console.log('Error getting location:', error)
          this.location = { latitude: null, longitude: null }
        }
      );
    }
  }

  private sendLocation(email: string, latitude: number, longitude: number) {
    console.log('lat:', latitude, 'lon:', longitude)
    this.apiService.post('api/user/user-location',
      {
        email: email,
        latitude: latitude,
        longitude: longitude
      }
    ).subscribe({
      next: (response) => {
        console.log('Location :', response)
      },
      error: (error) => {
        console.error('Location error', error)
      }
    });
  }
}
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
