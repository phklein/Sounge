export default interface CommentSaveRequestDto {
    userId: string,
    text: string,
    mediaUrl: ArrayBuffer
}