import { ShowCaseSection } from "./show-case-section";

/**
 * Rappresenta i dati di una vetrina (showcase) che contengono diverse sezioni di showcase.
 */
export interface ShowCaseData {
    sections: ShowCaseSection[];    // Un array di oggetti `ShowCaseSection` che rappresentano le sezioni della vetrina.
}
