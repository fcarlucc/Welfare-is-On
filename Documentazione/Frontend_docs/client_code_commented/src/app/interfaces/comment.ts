export interface Comment {
    id: number;            // Identificatore unico del commento
    serviceId: number;    // Identificatore del servizio a cui il commento si riferisce
    userId: number;       // Identificatore dell'utente che ha scritto il commento
    fullName: string;     // Nome completo dell'utente che ha scritto il commento
    content: string;      // Contenuto testuale del commento
    commentedAt: Date;   // Data e ora in cui è stato scritto il commento
}
