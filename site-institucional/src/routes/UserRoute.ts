
import http from '../http-commn'

import IUserRequestDto from '../dto/IUserRequestDto'
import IUserLoginRequestDto from '../dto/IUserLoginRequestDto'
import IUserResponseDto from '../dto/IUserResponseDto'

const saveAndLogin = (data: IUserRequestDto) => {
    return http.post<IUserResponseDto>("/users", data)
}

const login = (data: IUserLoginRequestDto) => {
    return http.post<IUserResponseDto>("/users/auth", data)
}

const joinGroup = (idUser: any, idGroup: any) => {
    return http.post<void>(`/users/${idUser}/group/${idGroup}`)
}

const leaveGroup = (idUser: any, idGroup: any) => {
    return http.delete<void>(`/users/${idUser}/group/${idGroup}`)
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

const ArtistService = {
    saveAndLogin,
    login,
    joinGroup,
    leaveGroup,
}
  
export default ArtistService;
