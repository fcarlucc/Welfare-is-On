import { CardService } from "./card-service";

/**
 * Rappresenta una sezione di servizi acquistati.
 */
export interface PurchasedSection {
    title: string;                      // Il titolo della sezione di servizi acquistati.
    infoShowCaseDto: CardService[];    // Un array di oggetti `CardService` che rappresentano i servizi inclusi nella sezione.
}
