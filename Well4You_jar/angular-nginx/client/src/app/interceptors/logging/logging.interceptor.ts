import { HttpInterceptorFn, HttpRequest, HttpHandlerFn, HttpEvent, HttpEventType } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

export const loggingInterceptor: HttpInterceptorFn = (
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
): Observable<HttpEvent<unknown>> => {
  const started = Date.now();
  console.log(`Request for ${req.urlWithParams} started at ${started}`);

  return next(req).pipe(
    tap({
      next: event => {
        if (event.type === HttpEventType.Response) {
          const elapsed = Date.now() - started;
          console.log(`Request for ${req.urlWithParams} took ${elapsed} ms.`);
        }
      },
      error: error => {
        const elapsed = Date.now() - started;
        console.error(`Request for ${req.urlWithParams} failed after ${elapsed} ms.`, error);
      }
    })
  );
};

// import { HttpInterceptorFn, HttpRequest, HttpHandlerFn, HttpEvent, HttpErrorResponse, HttpResponse } from '@angular/common/http';
// import { Observable, throwError, of, tap } from 'rxjs';
// // import { MessageService } from './message.service'; // Ensure you have a MessageService

// export const loggingInterceptor: HttpInterceptorFn = (req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> => {
//   const started = Date.now();
//   const logMessage = `[HTTP ${req.method}] ${req.urlWithParams}`; // Customize log message format

//   return next.handle(req).pipe(
//     tap({
//       // Success handler
//       next: (event: HttpEvent<any>) => {
//         if (event instanceof HttpResponse) {
//           const elapsed = Date.now() - started;
//           const responseLog = `${logMessage} ${elapsed}ms ${event.status}`;
//           // Log the response through your MessageService
//           logResponse(responseLog);
//         }
//       },

//       // Error handler
//       error: (error: HttpErrorResponse) => {
//         const elapsed = Date.now() - started;
//         const errorLog = `${logMessage} ${elapsed}ms ${error.status} - ${error.message}`;
//         // Log the error through your MessageService as an error
//         logError(errorLog, 'error');
//         return throwError(() => new Error(error)); // Rethrow the error
//       }
//     })
//   );

//   function logResponse(message: string) {
//     // this.messageService.add(message); // Assume your MessageService has an 'add' method
//   }

//   function logError(message: string, type: string = 'error') {
//     // this.messageService.add(message, type); // Assume your MessageService has an 'add' method with type support
//   }
// };
