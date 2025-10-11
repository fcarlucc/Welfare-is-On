export interface Comment {
    id: number;
    serviceId: number;
    userId: number;
    fullName: string;
    content: string;
    commentedAt: Date;
}
