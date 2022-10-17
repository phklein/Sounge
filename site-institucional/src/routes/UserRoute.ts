
import http from '../http-commn'

import UserRequestDto from '../dto/request/UserRequestDto'
import UserLoginRequestDto from '../dto/request/UserLoginRequestDto'
import UserResponseDto from '../dto/response/UserResponseDto'
import UserProfileResponseDto from '../dto/response/UserProfileResponseDto'
import PictureUpdateRequestDto from '../dto/request/PictureUpdateRequestDto'
import UserSimpleResponseDto from '../dto/response/UserSimpleResponseDto'
import { UserMatchResponseDto } from './../dto/response/UserMatchResponseDto'
// import NotificationSimpleResponseDto from './../dto/response/NotificationSimpleResponseDto'

const saveAndLogin = (data: UserRequestDto) => {
    return http.post<UserResponseDto>("/users", data)
}

const login = (data: UserLoginRequestDto) => {
    return http.post<UserResponseDto>(`/users/auth`, data)
}

const joinGroup = (idUser: any, idGroup: any) => {
    return http.post<void>(`/users/${idUser}/group/${idGroup}`)
}

const leaveGroup = (idUser: any) => {
    return http.delete<void>(`/users/${idUser}/group`)
}

const getProfileForId = (idUser: any, viewerId: any) => {
    return http.get<UserProfileResponseDto>(`/users/${idUser}`, {
        params: {
            viewerId: `${viewerId}`
        }
    })
}

const logout = (idUser: any) => {
    return http.delete<void>(`/users/${idUser}/auth`)
}

const getSimplePicture = (id: any) => {
    return http.get<PictureUpdateRequestDto>(`/users/${id}/picture`)
}

const updateSimplePicture = (id: any, data: PictureUpdateRequestDto) => {
    return http.patch<void>(`/users/${id}/photo`, data)
}

const updatePicture = (id: any, data: PictureUpdateRequestDto) => {
    return http.patch<void>(`/users/${id}/photo`, data)
}

const getMatchList = (parameters: any) => {
    return http.get<Array<UserMatchResponseDto>>(`/users/${parameters.id}/match`, { 
        params: parameters
    })
}

const getContactList = (id: any) => {
    return http.get<Array<UserSimpleResponseDto>>(`/users/${id}/contacts`)
}

const getUsersByName = (viewerId: any, name: any) => {
    return http.get<any>(`/users`, {
        params: {viewerId, nameLike: name}
    })
}

const importGroupText = (id: any, data: any) => {
    return http.post<void>(`/groups/upload/${id}`, data)
}

const checkNewMatches = (id: any) => {
    return http.get<Array<any>>(`/users/${id}/newMatches`)
}

const likeUser = (id: any, likedId: any) => {
    return http.post<void>(`/users/${id}/likeUser/${likedId}`)
}

const exportDownload = (id: any) => {
    return http.get<any>(`/users/download/${id}`)
}

const UserRoute = {
    exportDownload,
    saveAndLogin,
    login,
    joinGroup,
    leaveGroup,
    getProfileForId,
    logout,
    getSimplePicture,
    updateSimplePicture,
    updatePicture,
    getMatchList,
    getContactList,
    importGroupText,
    getUsersByName,
    checkNewMatches,
    likeUser
}
  
export default UserRoute
