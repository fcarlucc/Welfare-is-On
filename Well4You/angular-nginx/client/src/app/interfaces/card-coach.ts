import { SafeUrl } from "@angular/platform-browser";
import { FullCoach } from "./full-coach";

export interface CardCoach {
    id: number;
    imageId: string;
    imgUrl: SafeUrl;
    firstName: string;
    lastName: string;
    locationDto?: {latitude : number, longitude : number};
    location: string;
    division: string
    full?: FullCoach
}