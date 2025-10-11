import { Survey } from "./survey";

/**
 * Rappresenta un utente con dettagli personali e informazioni di autenticazione.
 */
export interface User {
    firstName: string;         // Il nome dell'utente.
    lastName: string;          // Il cognome dell'utente.
    dob: Date;                 // La data di nascita dell'utente.
    email: string;             // L'indirizzo email dell'utente.
    password: string;          // La password dell'utente.
    confirmPassword: string;   // La conferma della password dell'utente.
    isVerifiedEmail: boolean;  // Indica se l'email dell'utente è stata verificata.
    survey?: Survey;           // Un oggetto `Survey` che contiene informazioni facoltative sull'utente.
}
