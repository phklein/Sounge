
import { StateEnum } from '../../enums/StateEnum';
import { SexEnum } from '../../enums/SexEnum';
import { GenreNameEnum } from '../../enums/GenreNameEnum';
import { RoleNameEnum } from '../../enums/RoleNameEnum';
import { SkillLevelEnum } from '../../enums/SkillLevelEnum';

export default interface IUserRequestDto {
    email: string,
    password: string,
    name: string,
    sex: SexEnum,
    description: string,
    birthDate: string,
    state: StateEnum,
    city: string,
    likedGenres: GenreNameEnum[],
    roles: RoleNameEnum[],
    skillLevel: SkillLevelEnum
}

