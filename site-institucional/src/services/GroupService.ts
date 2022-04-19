
import http from '../http-commn'
import IGroupData from '../types/IGroupData'

const getAllGroup = () => {
    return http.get<Array<IGroupData>>('/users/group')
}

const getGroupById = (id: any) => {
    return http.get<IGroupData>(`/users/group/${id}`)
}

const createGroup = (data: IGroupData) => {
    return http.post<IGroupData>("/users/group", data)
}
  
const updateGroupById = (id: any, data: IGroupData) => {
    return http.put<any>(`/users/group/${id}`, data)
}
  
const removeGroupById = (id: any) => {
    return http.delete<any>(`/users/group/${id}`)
}

const GroupService = {
    getAllGroup,
    getGroupById,
    createGroup,
    updateGroupById,
    removeGroupById,
}
  
export default GroupService;
