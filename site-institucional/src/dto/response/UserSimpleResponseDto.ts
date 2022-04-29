import IRoleSimpleResponseDto from "./RoleSimpleResponseDto";

export default interface IUserSimpleResponseDto {
    id: number,
    name: string,
    profilePic: string,
    roles: IRoleSimpleResponseDto[],
    leader: boolean
}