import { Comment } from "./comment";
import { Location } from "./location";

export interface FullCard {
    description: string;   // Descrizione dettagliata della carta
    price: number;         // Prezzo della carta
    discount: number;      // Percentuale di sconto applicato
    likes: number;         // Numero di "mi piace" ricevuti
    liked: boolean;        // Indica se l'utente ha messo "mi piace"
    url: string;           // URL dell'immagine o del link della carta
    purchased: boolean;    // Indica se la carta è stata acquistata
    position?: Location;   // Posizione associata alla carta (opzionale)
    distance?: number;     // Distanza dalla posizione dell'utente (opzionale)
    comments: Comment[];   // Commenti associati alla carta
}
