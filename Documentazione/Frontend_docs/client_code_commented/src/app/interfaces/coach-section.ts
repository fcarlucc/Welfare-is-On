import { CardCoach } from "./card-coach";

export interface CoachSection {
    title: string;                 // Titolo della sezione
    infoShowCaseDto: CardCoach[];  // Array di oggetti `CardCoach` che rappresentano i coach in questa sezione
}
