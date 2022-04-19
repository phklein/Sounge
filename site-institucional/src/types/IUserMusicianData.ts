
import { IEstiloMusical, ICategoriaMusica } from './../components/MultiForm';

export default interface IUserMusicianData {
    nome: string,
    email: string,
    cpf: number | undefined,
    estado: string,
    cidade: string,
    senha: string,
    confirmarSenha: string,
    tipoUsuario: string,
    estiloMusical: IEstiloMusical[],
    categoriaMusicas: ICategoriaMusica[],
    nivel: string
}

