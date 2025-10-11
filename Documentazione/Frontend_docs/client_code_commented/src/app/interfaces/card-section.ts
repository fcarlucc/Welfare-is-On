import { CardService } from "./card-service";

export interface CardSection {
    title: string;                      // Il titolo della sezione.
    infoShowCaseDto: CardService[];    // Un array di oggetti `CardService` che forniscono informazioni sulla sezione.
}
