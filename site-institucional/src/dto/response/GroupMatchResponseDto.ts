
import { StateEnum } from "../../enums/StateEnum"
import GenreSimpleResponseDto from "./GenreSimpleResponseDto"
import RoleSimpleResponseDto from "./RoleSimpleResponseDto"

export default interface GroupMatchResponseDto {
    id: number,
    leaderId: number,
    name: string,
    profilePic: string,
    leaderState: StateEnum,
    leaderCity: string,
    description: string,
    genres: GenreSimpleResponseDto[],
    size: number,
    rolesFilled: RoleSimpleResponseDto[],
    age: number,
    leaderDistance: number,
    relevance: number,
    leaderHasSignature: boolean,
    leaderLatitude: number,
    leaderLongitude: number
}