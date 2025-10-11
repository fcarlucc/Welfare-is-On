import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { ErrorApiHandlerService } from '../error-api-handler/error-api-handler.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private serverUrl = 'https://localhost:8443';  // URL del server

  constructor(
    private http: HttpClient,
    private errorHandler: ErrorApiHandlerService  // Servizio per gestire gli errori
  ) {}

  /**
   * Gestisce gli errori HTTP.
   * @param error - L'errore HTTP da gestire.
   * @returns - Un Observable che emette un errore.
   */
  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Unknown error occurred';

    if (error.error instanceof ErrorEvent) {
      // Errore lato client
      console.error('An error occurred:', error.error.message);
      errorMessage = error.error.message;
    } else {
      // Errore lato server
      console.error(`Backend returned code ${error.status}, body was: ${error.error}`);
      if (error.error && error.error.error) {
        errorMessage = error.error.error;
      } else {
        errorMessage = `Error code: ${error.status}, message: ${error.message}`;
      }
    }

    this.errorHandler.handleError(errorMessage); // Usa il servizio per gestire l'errore
    return throwError(() => new Error(errorMessage));
  }

  /**
   * Gestisce la risposta fetch e lancia un errore se la risposta non è ok.
   * @param response - La risposta fetch da gestire.
   * @returns - I dati JSON della risposta.
   * @throws {Error} - Se la risposta non è ok.
   */
  private async handleFetchResponse(response: Response): Promise<any> {
    if (!response.ok) {
      const errorData = await response.json();
      const errorMessage = errorData.error || response.statusText;
      throw new Error(errorMessage);
    }
    return response.json();
  }

  /**
   * Effettua una richiesta GET e ritorna un Observable di tipo generico.
   * @param endpoint - Il percorso dell'endpoint API.
   * @param params - (Opzionale) I parametri della query.
   * @param headers - (Opzionale) Le intestazioni HTTP.
   * @returns - Un Observable con i dati della risposta.
   */
  get<T>(endpoint: string, params?: HttpParams, headers?: HttpHeaders): Observable<T> {
    const options = { params, headers, withCredentials: true };
    return this.http.get<T>(`${this.serverUrl}/${endpoint}`, options)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Effettua una richiesta GET usando fetch e ritorna una Promise.
   * @param url - L'URL della richiesta.
   * @returns - Una Promise con i dati JSON della risposta.
   * @throws {Error} - Se la richiesta fallisce o la risposta non è ok.
   */
  async get2(url: string): Promise<any> {
    try {
      const response = await fetch(url, {
        credentials: 'omit'
      });
      return this.handleFetchResponse(response);
    } catch (error) {
      console.error('Error in ApiService GET:', error);
      throw error;
    }
  }

  /**
   * Recupera un'immagine come Blob.
   * @param imageId - L'ID dell'immagine.
   * @returns - Un Observable con il Blob dell'immagine.
   */
  getImage(imageId: string): Observable<Blob> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http.get(`${this.serverUrl}/api/image/${imageId}`, { headers, responseType: 'blob', withCredentials: true });
  }

  /**
   * Recupera un'immagine come Blob e ritorna una Promise.
   * @param imageId - L'ID dell'immagine.
   * @returns - Il Blob dell'immagine.
   * @throws {Error} - Se la richiesta fallisce o l'immagine non viene recuperata.
   */
  async getImageAsPromise(imageId: string): Promise<Blob> {
    const imageBlob = await this.getImage(imageId).toPromise();
    if (!imageBlob) {
      throw new Error('Failed to fetch image');
    }
    return imageBlob;
  }

  /**
   * Effettua una richiesta POST e ritorna un Observable di tipo generico.
   * @param endpoint - Il percorso dell'endpoint API.
   * @param body - Il corpo della richiesta.
   * @param params - (Opzionale) I parametri della query.
   * @param headers - (Opzionale) Le intestazioni HTTP.
   * @returns - Un Observable con i dati della risposta.
   */
  post<T>(endpoint: string, body: any, params?: HttpParams, headers?: HttpHeaders): Observable<T> {
    const options = { params, headers, withCredentials: true };
    return this.http.post<T>(`${this.serverUrl}/${endpoint}`, body, options)
      .pipe(
        tap(response => {
          console.log('Response from server:', response);
        }),
        catchError(this.handleError)
      );
  }

  /**
   * Effettua una richiesta POST e ritorna l'intera risposta HTTP.
   * @param endpoint - Il percorso dell'endpoint API.
   * @param body - Il corpo della richiesta.
   * @param params - (Opzionale) I parametri della query.
   * @param headers - (Opzionale) Le intestazioni HTTP.
   * @returns - Un Observable con la risposta HTTP.
   */
  post2<T extends HttpResponse<T>>(endpoint: string, body: any, params?: HttpParams, headers?: HttpHeaders): Observable<HttpResponse<T>> {
    const options = { params, headers, observe: 'response' as 'body', withCredentials: true };
    return this.http.post<T>(`${this.serverUrl}/${endpoint}`, body, options)
      .pipe(
        tap(response => {
          console.log('Full Response from server:', response);
        }),
        catchError(this.handleError)
      );
  }

  /**
   * Effettua una richiesta POST senza parametri e ritorna un Observable di tipo generico.
   * @param endpoint - Il percorso dell'endpoint API.
   * @param body - Il corpo della richiesta.
   * @param headers - (Opzionale) Le intestazioni HTTP.
   * @returns - Un Observable con i dati della risposta.
   */
  post3<T>(endpoint: string, body: any, headers?: HttpHeaders): Observable<T> {
    const options = { headers, withCredentials: true };
    return this.http.post<T>(`${this.serverUrl}/${endpoint}`, body, options)
      .pipe(
        tap(response => {
          console.log('Response from server:', response);
        }),
        catchError(this.handleError)
      );
  }

  /**
   * Effettua una richiesta PUT e ritorna un Observable di tipo generico.
   * @param endpoint - Il percorso dell'endpoint API.
   * @param body - Il corpo della richiesta.
   * @param params - (Opzionale) I parametri della query.
   * @param headers - (Opzionale) Le intestazioni HTTP.
   * @returns - Un Observable con i dati della risposta.
   */
  put<T>(endpoint: string, body: any, params?: HttpParams, headers?: HttpHeaders): Observable<T> {
    const options = { params, headers, withCredentials: true };
    return this.http.put<T>(`${this.serverUrl}/${endpoint}`, body, options)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Effettua una richiesta PATCH e ritorna un Observable di tipo generico.
   * @param endpoint - Il percorso dell'endpoint API.
   * @param body - Il corpo della richiesta.
   * @param params - (Opzionale) I parametri della query.
   * @param headers - (Opzionale) Le intestazioni HTTP.
   * @returns - Un Observable con i dati della risposta.
   */
  patch<T>(endpoint: string, body: any, params?: HttpParams, headers?: HttpHeaders): Observable<T> {
    const options = { params, headers, withCredentials: true };
    return this.http.patch<T>(`${this.serverUrl}/${endpoint}`, body, options)
      .pipe(
        catchError(this.handleError)
      );
  }

  /**
   * Effettua una richiesta DELETE e ritorna un Observable di tipo generico.
   * @param endpoint - Il percorso dell'endpoint API.
   * @param body - (Opzionale) Il corpo della richiesta.
   * @param params - (Opzionale) I parametri della query.
   * @param headers - (Opzionale) Le intestazioni HTTP.
   * @returns - Un Observable con i dati della risposta.
   */
  delete<T>(endpoint: string, body: any, params?: HttpParams, headers?: HttpHeaders): Observable<T> {
    const options = { params, headers, withCredentials: true };
    return this.http.delete<T>(`${this.serverUrl}/${endpoint}`, options)
      .pipe(
        catchError(this.handleError)
      );
  }
}
