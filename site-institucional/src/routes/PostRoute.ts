import PostSaveRequestDto from '../dto/request/PostSaveRequestDto'
import http from '../http-commn'

const save = (data: any) => {
    return http.post<number>(`/posts`, data)
}

const PostRoute = {
   save
}

export default PostRoute
 
// import CommentSaveRequestDto from '../dto/request/CommentSaveResquestDto';
// import http from '../http-commn'

// const saveComment = (postId: any, body:CommentSaveRequestDto) => {
//     return http.post<any>(`/posts/${postId}/comments`, body)
// }

// const PostRoute = {
//     saveComment
// }
  
// export default PostRoute;