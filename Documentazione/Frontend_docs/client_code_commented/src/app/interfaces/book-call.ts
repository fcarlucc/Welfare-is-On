import { TimeSlot } from "./time-slot";

export interface BookCall {
    day?: Date;         // La data della prenotazione. È facoltativa.
    slot?: TimeSlot;   // L'orario della prenotazione. È facoltativo e dipende dall'interfaccia `TimeSlot`.
    text?: string;     // Eventuali note o dettagli aggiuntivi. È facoltativo.
    userId?: number;   // L'ID dell'utente che prenota la chiamata. È facoltativo.
    coachId?: number;  // L'ID del coach con cui si prenota la chiamata. È facoltativo.
}
