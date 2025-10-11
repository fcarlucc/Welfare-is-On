import { TimeSlot } from "./time-slot";

export interface BookCall {
    day?: Date;
    slot?: TimeSlot;
    text?: string;
    userId?: number
    coachId?: number
}