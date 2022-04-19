
import http from '../http-commn'
import IArtistData from '../types/IArtistData'

const getAllArtist = () => {
    return http.get<Array<IArtistData>>('/users/artists')
}

const getArtistById = (id: any) => {
    return http.get<IArtistData>(`/users/artists/${id}`)
}

const createArtist = (data: IArtistData) => {
    return http.post<IArtistData>("/users/artists", data)
}
  
const updateArtistById = (id: any, data: IArtistData) => {
    return http.put<any>(`/users/artists/${id}`, data)
}
  
const removeArtistById = (id: any) => {
    return http.delete<any>(`/users/artists/${id}`)
}

const ArtistService = {
    getAllArtist,
    getArtistById,
    createArtist,
    updateArtistById,
    removeArtistById,
}
  
export default ArtistService;
