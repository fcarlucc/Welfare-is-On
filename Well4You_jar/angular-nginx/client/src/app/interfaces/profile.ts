export interface Profile {
    firstName: string;
    lastName: string;
    dob: string;
    email: string;
    maritalStatusName: string;
    hasChildren?: boolean;
    hasElderlyParents: boolean;
    interests?: string[];
    savedMoney: number;
    titleName: string;
}
