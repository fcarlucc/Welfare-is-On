import { SafeUrl } from "@angular/platform-browser";
import { FullCard } from "./full-card";

export interface CardService {
    id: number;                    // Identificatore univoco per il servizio.
    pillarName: string;            // Nome del pilastro o della categoria a cui appartiene il servizio.
    imageId: string;               // Identificatore dell'immagine associata al servizio.
    imgUrl?: SafeUrl;              // URL sicuro dell'immagine, facoltativo.
    title: string;                 // Titolo del servizio.
    full?: FullCard;               // Oggetto `FullCard` che contiene dettagli completi sul servizio, facoltativo.
}
