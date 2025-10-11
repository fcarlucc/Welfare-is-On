import { Comment } from "./comment";
import { Location } from "./location";

export interface FullCard {
    description: string;
    price: number;
    discount: number;
    likes: number;
    liked: boolean;
    url: string;
    purchased: boolean;
    position?: Location;
    distance?: number;
    comments: Comment[];
}