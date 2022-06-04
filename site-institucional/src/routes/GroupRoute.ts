
import http from '../http-commn'

import IGroupRequestDto from '../dto/request/GroupRequestDto'
import GroupMatchResponseDto from '../dto/response/GroupMatchResponseDto'

const save = (data: IGroupRequestDto) => {
    return http.post<any>("/groups", data)
}

const getAllGroup = () => {
    return http.get<Array<IGroupRequestDto>>("/groups")
}

const deleteGroupById = (idGroup: number) => {
    return http.delete<void>(`/groups/${idGroup}`)
}

const getMatchList = (parameters: any) => {
    return http.get<Array<GroupMatchResponseDto>>(`/groups/${parameters.id}/match`, {
        params: parameters
    })
}

const GroupService = {
    save,
    getAllGroup,
    getMatchList
}
  
export default GroupService;
