import IUserSimpleResponseDto from "./UserSimpleResponseDto";

export default interface IPostListResponseDto {
    id: number,
    text: string,
    mediaUrl: ArrayBuffer,
    hoursPast: number,
    user: IUserSimpleResponseDto,
    likeCount: number,
    commentCount: number
} 