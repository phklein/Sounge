import IRoleSimpleResponseDto from "./IRoleSimpleResponseDto";

export default interface IUserSimpleResponseDto {
    id: number,
    name: string,
    profilePic: string,
    roles: IRoleSimpleResponseDto[],
    leader: boolean
}