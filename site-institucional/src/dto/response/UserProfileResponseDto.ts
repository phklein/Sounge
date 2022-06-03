
import { SkillLevelEnum } from '../../enums/SkillLevelEnum'
import PostListResponseDto from './PostListResponseDto'
import GroupSimpleResponseDto from './GroupSimpleResponseDto'
import GenreSimpleResponseDto from './GenreSimpleResponseDto'
import RoleSimpleResponseDto from './RoleSimpleResponseDto'

export default interface IUserProfileResponseDto {
    id: number,
    name: string,
    profilePic: string,
    banner: string | ArrayBuffer | null,
    leader: boolean,
    postList: PostListResponseDto[],
    spotifyID: string,
    description: string,
    isOnline: boolean,
    skillLevel: SkillLevelEnum,
    group: GroupSimpleResponseDto,
    likedGenres: GenreSimpleResponseDto[],
    roles: RoleSimpleResponseDto[],
    age: number
}

