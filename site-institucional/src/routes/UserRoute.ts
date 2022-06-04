
import http from '../http-commn'

import { UserMatchResponseDto } from './../dto/response/UserMatchResponseDto';
import UserRequestDto from '../dto/request/UserRequestDto'
import UserLoginRequestDto from '../dto/request/UserLoginRequestDto'
import UserResponseDto from '../dto/response/UserResponseDto'
import UserProfileResponseDto from '../dto/response/UserProfileResponseDto'
import PictureUpdateRequestDto from '../dto/request/PictureUpdateRequestDto'
import UserSimpleResponseDto from '../dto/response/UserSimpleResponseDto';

const saveAndLogin = (data: UserRequestDto) => {
    return http.post<UserResponseDto>("/users", data)
}

const login = (data: UserLoginRequestDto) => {
    return http.post<UserResponseDto>(`/users/auth`, data)
}

const joinGroup = (idUser: any, idGroup: any) => {
    return http.post<void>(`/users/${idUser}/group/${idGroup}`)
}

const leaveGroup = (idUser: any, idGroup: any) => {
    return http.delete<void>(`/users/${idUser}/group/${idGroup}`)
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

const UserRoute = {
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
    getContactList
}
  
export default UserRoute;
