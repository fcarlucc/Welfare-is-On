import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { ErrorApiHandlerService } from '../error-api-handler/error-api-handler.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private serverUrl = 'https://localhost:8443';

  constructor(
    private http: HttpClient,
    private errorHandler: ErrorApiHandlerService
  ) {}

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Unknown error occurred';

    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
      errorMessage = error.error.message;
    } else {
      console.error(`Backend returned code ${error.status}, body was: ${error.error}`);
      if (error.error && error.error.error) {
        errorMessage = error.error.error;
      } else {
        errorMessage = `Error code: ${error.status}, message: ${error.message}`;
      }
    }

    this.errorHandler.handleError(errorMessage); // Use the service to handle the error
    return throwError(() => new Error(errorMessage));
  }

  private async handleFetchResponse(response: Response): Promise<any> {
    if (!response.ok) {
      const errorData = await response.json();
      const errorMessage = errorData.error || response.statusText;
      throw new Error(errorMessage);
    }
    return response.json();
  }

  get<T>(endpoint: string, params?: HttpParams, headers?: HttpHeaders): Observable<T> {
    const options = { params, headers, withCredentials: true };
    return this.http.get<T>(`${this.serverUrl}/${endpoint}`, options)
      .pipe(
        catchError(this.handleError)
      );
  }

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

  getImage(imageId: string): Observable<Blob> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http.get(`${this.serverUrl}/api/image/${imageId}`, { headers, responseType: 'blob', withCredentials: true });
  }

  async getImageAsPromise(imageId: string): Promise<Blob> {
    const imageBlob = await this.getImage(imageId).toPromise();
    if (!imageBlob) {
      throw new Error('Failed to fetch image');
    }
    return imageBlob;
  }

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

  put<T>(endpoint: string, body: any, params?: HttpParams, headers?: HttpHeaders): Observable<T> {
    const options = { params, headers, withCredentials: true };
    return this.http.put<T>(`${this.serverUrl}/${endpoint}`, body, options)
      .pipe(
        catchError(this.handleError)
      );
  }

  patch<T>(endpoint: string, body: any, params?: HttpParams, headers?: HttpHeaders): Observable<T> {
    const options = { params, headers, withCredentials: true };
    return this.http.patch<T>(`${this.serverUrl}/${endpoint}`, body, options)
      .pipe(
        catchError(this.handleError)
      );
  }

  delete<T>(endpoint: string, body: any, params?: HttpParams, headers?: HttpHeaders): Observable<T> {
    const options = { params, headers, withCredentials: true };
    return this.http.delete<T>(`${this.serverUrl}/${endpoint}`, options)
      .pipe(
        catchError(this.handleError)
      );
  }
}
