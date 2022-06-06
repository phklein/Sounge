
import { GenreNameEnum } from '../../enums/GenreNameEnum'

export default interface IGroupRequestDto {
    name: string,
    description: string,
    creationDate: string,
    genres: any[]
}

