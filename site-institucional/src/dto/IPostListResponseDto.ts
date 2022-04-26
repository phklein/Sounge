import IUserSimpleResponseDto from "./IUserSimpleResponseDto";

export default interface IPostListResponseDto {
    id: number,
    text: string,
    mediaUrl: string,
    hoursPast: number,
    user: IUserSimpleResponseDto,
    likeCount: number,
    commentCount: number
} 