
import { SkillLevelEnum } from '../../enums/SkillLevelEnum'
import IPostListResponseDto from './PostListResponseDto'
import IGroupSimpleResponseDto from './GroupSimpleResponseDto'
import IGenreSimpleResponseDto from './GenreSimpleResponseDto'
import IRoleSimpleResponseDto from './RoleSimpleResponseDto'

export default interface IUserProfileResponseDto {
    id: number,
    name: string,
    profilePic: string,
    banner: string | ArrayBuffer | null,
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

