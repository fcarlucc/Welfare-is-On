export interface Profile {
    firstName: string;          // Nome dell'utente
    lastName: string;           // Cognome dell'utente
    dob: string;                // Data di nascita dell'utente in formato stringa (ISO 8601 o un altro formato standard)
    email: string;             // Indirizzo email dell'utente
    maritalStatusName: string; // Stato civile dell'utente (ad es. "Single", "Married", ecc.)
    hasChildren?: boolean;     // Indica se l'utente ha figli. È facoltativo e può essere undefined.
    hasElderlyParents: boolean; // Indica se l'utente ha genitori anziani
    interests?: string[];       // Elenco degli interessi dell'utente. È facoltativo e può essere undefined.
    savedMoney: number;         // Ammontare di denaro risparmiato dall'utente
    titleName: string;          // Titolo dell'utente (ad es. "Dr.", "Mr.", "Ms.", ecc.)
}
