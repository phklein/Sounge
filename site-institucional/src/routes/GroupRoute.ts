
import http from '../http-commn'

import IGroupRequestDto from '../dto/request/GroupRequestDto'
import GroupMatchResponseDto from '../dto/response/GroupMatchResponseDto'

const save = (data: any) => {
    return http.post<any>("/groups", data)
}

const getAllGroup = () => {
    return http.get<Array<IGroupRequestDto>>("/groups")
}

const deleteGroupById = (idGroup: number) => {
    return http.delete<void>(`/groups/${idGroup}`)
}

const getGroupPageById = (idGroup: number) => {
    return http.get<any>(`/groups/${idGroup}`)
}

const changeGroupImages = (idGroup: number, data: any) => {
    return http.patch<any>(`/groups/${idGroup}/photo`, data)
}

const getMatchList = (parameters: any) => {
    return http.get<Array<GroupMatchResponseDto>>(`/groups/match`, {
        params: parameters
    })
}

const GroupService = {
    save,
    getAllGroup,
    getMatchList,
    getGroupPageById,
    deleteGroupById,
    changeGroupImages,
}
  
export default GroupService;
