import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'

import { Register } from '../pages/register/Register'
import { RegisterTypeUser } from '../pages/register/RegisterTypeUser'
import { RegisterArtist } from '../pages/register/RegisterArtist'
import { RegisterGroup } from '../pages/register/RegisterGroup'

import ArtistService from '../services/ArtistService'
import GroupService from '../services/GroupService'

import IArtistData from '../types/IArtistData'
import IGroupData from '../types/IGroupData'

import { TypeUserEnum } from '../enums/TypeUserEnum'
import { StateEnum } from '../enums/StateEnum'

export interface IFormState {
    step: number;
    nome: string;
    email: string;
    cpf: number | undefined;
    estado: StateEnum;
    cidade: string;
    senha: string;
    confirmarSenha: string;
    tipoUsuario: TypeUserEnum;
    estiloMusical: IEstiloMusical[];
    categoriaMusicas: ICategoriaMusica[];
    nivel: string;
}

export interface ICategoriaMusica {
    idCategoria: number;
}

export interface IEstiloMusical {
    idEstiloMusical: number;
}

const defaultValue: IFormState = {
    step: 1,
    nome: '',
    email: '',
    cpf: undefined,
    estado: StateEnum.NULL,
    cidade: '',
    senha: '',
    confirmarSenha: '',
    tipoUsuario: TypeUserEnum.NULL,
    estiloMusical: [],
    categoriaMusicas: [],
    nivel: ''
}

export function MultiForm() {
    const navigate = useNavigate()

    const [formState, setFormState] = useState<IFormState>(defaultValue)
    
    const handleFieldChange = (value: any, fieldName: string) => {
        setFormState({
            ...formState,
            [fieldName]: value
        })
    }

    const handleNextStep = () => {
        setFormState({
            ...formState,
            step: formState.step + 1
        })
    }

    const handlePreviousStep = () => {
        setFormState({
            ...formState,
            step: formState.step - 1
        })
    }

    const renderForms = () => {
        if (formState.step === 1) {
            console.log(formState)
            return <Register nextStep={handleNextStep} handleChange={handleFieldChange} formState={formState} />
        } else if (formState.step === 2) {
            console.log(formState)
            return <RegisterTypeUser previousStep={handlePreviousStep} nextStep={handleNextStep} handleChange={handleFieldChange} formState={formState} />
        } else if (formState.step === 3) {
            console.log(formState)

            if (formState.tipoUsuario == TypeUserEnum.ARTIST) {
                console.log(formState)
                return <RegisterArtist previousStep={handlePreviousStep} nextStep={handleNextStep} handleChange={handleFieldChange} formState={formState} />
            }
            
            if (formState.tipoUsuario == TypeUserEnum.GROUP) {
                console.log(formState)
                return <RegisterGroup previousStep={handlePreviousStep} nextStep={handleNextStep} handleChange={handleFieldChange} formState={formState} />
            } 
        } else if (formState.step === 4) {
            console.log(formState)
            
            if (formState.tipoUsuario == TypeUserEnum.ARTIST) {
                const user: IArtistData = {
                    nome: formState.nome,
                    email: formState.email,
                    cpf: formState.cpf,
                    estado: formState.estado,
                    cidade: formState.cidade,
                    senha: formState.senha,
                    confirmarSenha: formState.confirmarSenha,
                    tipoUsuario: formState.tipoUsuario,
                    estiloMusical: formState.estiloMusical,
                    categoriaMusicas: formState.categoriaMusicas,
                    nivel: formState.nivel
                }

                ArtistService.createArtist(user).then(() => {
                    alert('Cadastro realizado com sucesso (artista)')
                    navigate('/login')
                }).catch(() => {
                    alert('Erro ao cadastrar artista')
                    navigate('/')
                })
            }

            if (formState.tipoUsuario == TypeUserEnum.GROUP) {
                const user: IGroupData = {
                    nome: formState.nome,
                    email: formState.email,
                    cpf: formState.cpf,
                    estado: formState.estado,
                    cidade: formState.cidade,
                    senha: formState.senha,
                    confirmarSenha: formState.confirmarSenha,
                    tipoUsuario: formState.tipoUsuario,
                }

                GroupService.createGroup(user).then(() => {
                    alert('Cadastro realizado com sucesso (grupo)')
                    navigate('/login')
                }).catch(() => {
                    alert('Erro ao cadastrar grupo')
                    navigate('/')
                })
            }
        }

        return <></>
    }

    return (
        <>{renderForms()}</>
    )

}