import { CardService } from "./card-service";

/**
 * Rappresenta una sezione di una vetrina (showcase) con un titolo e una lista di servizi.
 */
export interface ShowCaseSection {
    title: string;                      // Il titolo della sezione dello showcase.
    infoShowCaseDto: CardService[];    // Un array di oggetti `CardService` che forniscono dettagli sui servizi mostrati nella sezione.
}
