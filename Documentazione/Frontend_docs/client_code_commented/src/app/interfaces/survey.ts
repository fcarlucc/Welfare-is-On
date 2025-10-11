import { Location } from "./location";

/**
 * Rappresenta un sondaggio (survey) con informazioni su stato civile, figli, genitori anziani, e altri dettagli.
 */
export interface Survey {
    title: string;                      // Il titolo del sondaggio.
    maritalStatusName: string;         // Lo stato civile dell'individuo.
    children?: boolean;                // Indica se l'individuo ha figli (opzionale).
    elderlyParents: boolean;           // Indica se l'individuo ha genitori anziani.
    location?: Location;               // La posizione dell'individuo (opzionale).
    interests?: string[];              // Un array di interessi dell'individuo (opzionale).
}
