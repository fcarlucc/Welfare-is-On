import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withFetch, withInterceptors, withXsrfConfiguration } from '@angular/common/http';

import { routes } from './app.routes';

import { authInterceptor } from './interceptors/auth/auth.interceptor';
import { loggingInterceptor } from './interceptors/logging/logging.interceptor';
import { headerInterceptor } from './interceptors/header/header.interceptor';
import { errorInterceptor } from './interceptors/error/error.interceptor';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { refreshTokenInterceptor } from './interceptors/refresh-token/refresh-token.interceptor';

/**
 * Configurazione dell'applicazione Angular.
 * Definisce i provider e le configurazioni per il routing, i client HTTP,
 * gli interceptor e le animazioni.
 */
export const appConfig: ApplicationConfig = {
  providers: [
    /**
     * Configura il rilevamento dei cambiamenti per Zone.js.
     * EventCoalescing consente di aggregare eventi in un'unica esecuzione di change detection.
     */
    provideZoneChangeDetection({ 
      eventCoalescing: true
    }),

    /**
     * Configura il router dell'applicazione con le rotte definite.
     */
    provideRouter(routes),

    /**
     * Configura il client HTTP con le opzioni:
     * - `withFetch()` per supportare la fetch API.
     * - `withInterceptors()` per aggiungere gli interceptor personalizzati.
     * - `withXsrfConfiguration()` per configurare la protezione XSRF.
     */
    provideHttpClient(
      withFetch(),
      withInterceptors([
        authInterceptor,              // Interceptor per gestire l'autenticazione
        refreshTokenInterceptor,      // Interceptor per gestire il refresh dei token
        loggingInterceptor,           // Interceptor per la registrazione delle richieste e delle risposte
        headerInterceptor,            // Interceptor per gestire gli header delle richieste
        errorInterceptor              // Interceptor per gestire gli errori delle richieste
      ]),
      withXsrfConfiguration({}) // Approfondire la configurazione XSRF se necessaria
    ),

    /**
     * Configura le animazioni asincrone per l'applicazione.
     */
    provideAnimationsAsync(),
  ]
};
