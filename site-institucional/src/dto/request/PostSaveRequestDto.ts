import { GenreNameEnum } from "../../enums/GenreNameEnum";

export default interface PostSaveRequestDto {
    userId: number, // backend = long / front = number
    text: string, // backend = text / front = string
    mediaUrl?: ArrayBuffer, // bytes
    genres?: GenreNameEnum[] // backend = arraylist / front = vetor
}