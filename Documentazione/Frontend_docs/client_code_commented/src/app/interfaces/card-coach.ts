import { SafeUrl } from "@angular/platform-browser";
import { FullCoach } from "./full-coach";

export interface CardCoach {
    id: number;                            // Un identificativo univoco per il coach.
    imageId: string;                      // L'ID dell'immagine del coach, usato per recuperare l'immagine.
    imgUrl: SafeUrl;                     // L'URL sicuro dell'immagine del coach.
    firstName: string;                   // Il nome del coach.
    lastName: string;                    // Il cognome del coach.
    locationDto?: {                      // Le coordinate geografiche del coach, facoltative.
        latitude: number;                // La latitudine del coach.
        longitude: number;               // La longitudine del coach.
    };
    location: string;                   // La posizione del coach sotto forma di stringa.
    division: string;                   // La divisione o il settore in cui opera il coach.
    full?: FullCoach;                  // Oggetto opzionale con dettagli completi sul coach, se disponibile.
}
