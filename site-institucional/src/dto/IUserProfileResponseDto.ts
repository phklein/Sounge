
import { SkillLevelEnum } from '../enums/SkillLevelEnum'
import IPostListResponseDto from './IPostListResponseDto'
import IGroupSimpleResponseDto from './IGroupSimpleResponseDto'
import IGenreSimpleResponseDto from './IGenreSimpleResponseDto'
import IRoleSimpleResponseDto from './IRoleSimpleResponseDto'

export default interface IUserProfileResponseDto {
    id: number,
    name: string,
    profilePic: string,
    leader: boolean,
    postList: IPostListResponseDto[],
    spotifyID: string,
    description: string,
    isOnline: boolean,
    skillLevel: SkillLevelEnum,
    group: IGroupSimpleResponseDto,
    likedGenres: IGenreSimpleResponseDto[],
    roles: IRoleSimpleResponseDto[],
    age: number
}

