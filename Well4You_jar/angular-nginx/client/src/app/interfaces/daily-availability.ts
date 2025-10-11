import { TimeSlot } from "./time-slot";

export interface DailyAvailability {
    day: Date;
    slots: TimeSlot[];
}
