import PostSaveRequestDto from '../dto/request/PostSaveRequestDto'
import http from '../http-commn'

const save = (data: any) => {
    return http.post<number>(`/posts`, data)
}

const PostRoute = {
   save
}

 
export default PostRoute;