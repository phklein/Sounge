
import http from '../http-commn'
import IUserMusicianData from '../types/IUserMusicianData'

const getAllUser = () => {
    return http.get<Array<IUserMusicianData>>('/users')
}

const getUserById = (id: any) => {
    return http.get<IUserMusicianData>(`/users/${id}`)
}

const createUserMusician = (data: IUserMusicianData) => {
    return http.post<IUserMusicianData>("/users", data)
}
  
const updateUserById = (id: any, data: IUserMusicianData) => {
    return http.put<any>(`/users/${id}`, data)
}
  
const removeUserById = (id: any) => {
    return http.delete<any>(`/users/${id}`)
}

const UserService = {
    getAllUser,
    getUserById,
    createUserMusician,
    updateUserById,
    removeUserById,
}
  
export default UserService;
