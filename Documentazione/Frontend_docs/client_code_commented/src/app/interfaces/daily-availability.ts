import { TimeSlot } from "./time-slot";

export interface DailyAvailability {
    day: Date;              // Data del giorno per cui è fornita la disponibilità
    slots: TimeSlot[];     // Array di intervalli di tempo disponibili per il giorno specificato
}
