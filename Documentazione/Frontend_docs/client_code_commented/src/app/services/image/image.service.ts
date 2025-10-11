import { Injectable } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ApiService } from '../api/api.service';

/**
 * Servizio per la gestione e recupero delle immagini.
 * Questo servizio si occupa di recuperare immagini dal server, sanitizzare le URL delle immagini
 * e fornire URL sicuri per l'utilizzo nelle viste.
 */
@Injectable({
  providedIn: 'root'
})
export class ImageService {

  /**
   * Crea un'istanza del servizio `ImageService`.
   * @param sanitizer - Il servizio per la sanitizzazione delle URL.
   * @param apiService - Il servizio per le chiamate API.
   */
  constructor(
    private sanitizer: DomSanitizer,
    private apiService: ApiService
  ) { }

  /**
   * Recupera un'immagine dal server e restituisce un URL sicuro.
   * Utilizza il servizio `ApiService` per recuperare l'immagine come Blob e poi
   * crea un URL oggetto che viene sanitizzato per l'uso sicuro.
   * @param imageId - L'ID dell'immagine da recuperare.
   * @returns Un `Promise` che risolve in un URL sicuro (`SafeUrl`) dell'immagine.
   * @throws Error se il recupero dell'immagine fallisce.
   */
  async fetchImage(imageId: string): Promise<SafeUrl> {
    let path: SafeUrl;

    try {
      const imageBlob = await this.apiService.getImageAsPromise(imageId);
      // this.logBlob(imageBlob); // Logga i dettagli del blob
      const objectURL = URL.createObjectURL(imageBlob);
      path = this.sanitizer.bypassSecurityTrustUrl(objectURL);
      // console.log('Sanitized Image URL:', path); // Logga l'URL sanitizzato
    } catch (error) {
      console.error('Image retrieval failed', error);
      throw error;
    }

    return path;
  }

  /**
   * Logga i dettagli del Blob dell'immagine come Data URL.
   * Questo metodo è utile per il debug per vedere il contenuto del Blob.
   * @param blob - Il Blob dell'immagine da loggare.
   */
  logBlob(blob: Blob): void {
    const reader = new FileReader();

    reader.onload = () => {
      console.log('Blob Data URL:', reader.result);
    };

    reader.readAsDataURL(blob);
  }
}
