import { SafeUrl } from "@angular/platform-browser";
import { FullCard } from "./full-card";

export interface CardService {
    id: number;
    pillarName: string;
    imageId: string;
    imgUrl?: SafeUrl;
    title: string;
    full?: FullCard;
}