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


export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ 
      eventCoalescing: true
    }),
    provideRouter(
      routes
    ),
    provideHttpClient(
      withFetch(),
      withInterceptors([
        authInterceptor,
        refreshTokenInterceptor,
        loggingInterceptor,
        headerInterceptor,
        errorInterceptor,
      ]),
      withXsrfConfiguration({}) //approfondire
    ), provideAnimationsAsync(),
  ]
};
