export interface Survey {
    title: string;
    maritalStatusName: string;
    children?: boolean;
    elderlyParents: boolean;
    location?: Location;
    interests?: string[];
}
