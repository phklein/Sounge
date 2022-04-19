import { useState } from 'react'

import { Register } from '../pages/Register'
import { RegisterMusician } from '../pages/RegisterMusician'
import { RegisterTypeUser } from '../pages/RegisterTypeUser'

import UserMusicianService from '../services/UserMusicianService'
import IUserMusicianData from '../types/IUserMusicianData'
import { useNavigate, Link } from 'react-router-dom'

export interface IFormState {
    step: number;
    nome: string;
    email: string;
    cpf: number | undefined;
    estado: string;
    cidade: string;
    senha: string;
    confirmarSenha: string;
    tipoUsuario: string;
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
    estado: '',
    cidade: '',
    senha: '',
    confirmarSenha: '',
    tipoUsuario: '',
    estiloMusical: [],
    categoriaMusicas: [],
    nivel: ''
}

export function MultiForm() {
    const navigate = useNavigate()

    const [formState, setFormState] = useState<IFormState>(defaultValue)
    
    const handleFieldChange = (value: string, fieldName: string) => {
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
            return <Register nextStep={handleNextStep} handleChange={handleFieldChange} formState={formState} />
        } else if (formState.step === 2) {
            return <RegisterTypeUser previousStep={handlePreviousStep} nextStep={handleNextStep} handleChange={handleFieldChange} formState={formState} />
        } else if (formState.step === 3) {
            return <RegisterMusician previousStep={handlePreviousStep} nextStep={handleNextStep} handleChange={handleFieldChange} formState={formState} />
        } else if (formState.step === 4) {

            // const user: IUserMusicianData = {
            //     nome: formState.nome,
            //     email: formState.email,
            //     cpf: formState.cpf,
            //     estado: formState.estado,
            //     cidade: formState.cidade,
            //     senha: formState.senha,
            //     confirmarSenha: formState.confirmarSenha,
            //     tipoUsuario: formState.tipoUsuario,
            //     estiloMusical: formState.estiloMusical[],
            //     categoriaMusicas: formState.categoriaMusicas[],
            //     nivel: formState.nivel
            // }
                    
            // UserMusicianService.createUserMusician(user).then(() => {
            //     navigate('/login')
            // })
        }

        return <></>
    }

    return (
        <>{renderForms()}</>
    )

}