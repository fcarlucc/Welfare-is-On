import { Survey } from "./survey";

export interface User {
    firstName: string;
    lastName: string;
    dob: Date;
    email: string;
    password: string;
    confirmPassword: string;
    isVerifiedEmail: boolean;
    survey?: Survey;
}
