import CommentSaveRequestDto from '../dto/request/CommentSaveResquestDto';
import http from '../http-commn'

const saveComment = (postId: any, body:CommentSaveRequestDto) => {
    return http.post<any>(`/posts/${postId}/comments`, body)
}

const PostRoute = {
    saveComment
}
  
export default PostRoute;