
import { IEstiloMusical, ICategoriaMusica } from '../components/MultiForm';

import { StateEnum } from '../enums/StateEnum';
import { TypeUserEnum } from '../enums/TypeUserEnum';

export default interface IArtistData {
    nome: string,
    email: string,
    cpf: number | undefined,
    estado: StateEnum,
    cidade: string,
    senha: string,
    confirmarSenha: string,
    tipoUsuario: TypeUserEnum,
    estiloMusical: IEstiloMusical[],
    categoriaMusicas: ICategoriaMusica[],
    nivel: string
}

