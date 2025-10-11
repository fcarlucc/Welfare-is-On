import { TimeSlot } from "./time-slot";

export interface DaySlot {
    slots?: TimeSlot[]; // Array opzionale di intervalli di tempo per il giorno specifico
}
