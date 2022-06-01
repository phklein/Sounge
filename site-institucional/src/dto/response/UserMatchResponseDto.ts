
import GenreSimpleResponseDto from "./GenreSimpleResponseDto"
import GroupSimpleResponseDto from "./GroupSimpleResponseDto"
import RoleSimpleResponseDto from "./RoleSimpleResponseDto"

import { SexEnum } from "../../enums/SexEnum"
import { SkillLevelEnum } from "../../enums/SkillLevelEnum"
import { StateEnum } from "../../enums/StateEnum"

export interface UserMatchResponseDto {
    id: number;
    name: string;
    profilePic: string;
    isLeader: boolean;
    sex: SexEnum;
    state: StateEnum;
    city: string;
    spotifyID: string;
    description: string;
    isOnline: boolean;
    skillLevel: SkillLevelEnum;
    group: GroupSimpleResponseDto;
    likedGenres: GenreSimpleResponseDto[];
    roles: RoleSimpleResponseDto[];
    age: number;
    distance: number;
    relevance: number;
    hasSignature: boolean;
    latitude: number;
    longitude: number;
}