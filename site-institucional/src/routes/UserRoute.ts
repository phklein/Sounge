
import http from '../http-commn'

import IUserRequestDto from '../dto/request/UserRequestDto'
import IUserLoginRequestDto from '../dto/request/UserLoginRequestDto'
import IUserResponseDto from '../dto/response/UserResponseDto'
import IUserProfileResponseDto from '../dto/response/UserProfileResponseDto'
import PictureUpdateRequestDto from '../dto/request/PictureUpdateRequestDto'

const saveAndLogin = (data: IUserRequestDto) => {
    return http.post<IUserResponseDto>("/users", data)
}

const login = (data: IUserLoginRequestDto) => {
    return http.post<IUserResponseDto>(`/users/auth`, data)
}

const joinGroup = (idUser: any, idGroup: any) => {
    return http.post<void>(`/users/${idUser}/group/${idGroup}`)
}

const leaveGroup = (idUser: any, idGroup: any) => {
    return http.delete<void>(`/users/${idUser}/group/${idGroup}`)
}

const getProfileForId = (idUser: any, viewerId: any) => {
    return http.get<IUserProfileResponseDto>(`/users/${idUser}?viewerId=${viewerId}`)
}

const logout = (idUser: any) => {
    return http.delete<void>(`/users/${idUser}/auth`)
}

const updatePicture = (id: any, data: PictureUpdateRequestDto) => {
    return http.patch<void>(`/users/${id}/picture`, data)
}



































const getAllArtist = () => {
    return http.get<Array<IUserRequestDto>>('/users/artists')
}

const getArtistById = (id: any) => {
    return http.get<IUserRequestDto>(`/users/artists/${id}`)
}

const createArtist = (data: IUserRequestDto) => {
    return http.post<IUserRequestDto>("/users/artists", data)
}
  
const updateArtistById = (id: any, data: IUserRequestDto) => {
    return http.put<any>(`/users/artists/${id}`, data)
}
  
const removeArtistById = (id: any) => {
    return http.delete<any>(`/users/artists/${id}`)
}

const UserRoute = {
    saveAndLogin,
    login,
    joinGroup,
    leaveGroup,
    getProfileForId,
    logout,
    updatePicture
}
  
export default UserRoute;
