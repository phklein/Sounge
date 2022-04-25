import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { useForm, SubmitHandler } from "react-hook-form"
import Swal from 'sweetalert2'
import withReactContent from 'sweetalert2-react-content'


import UserService from '../routes/UserRoute'

import { Register } from '../pages/register/Register'
import { Register2 } from '../pages/register/Register2'
import { RegisterConcluid } from '../pages/register/RegisterConcluid'

import IUserResponseDto from '../dto/IUserResponseDto'
import IUserRequestDto from '../dto/IUserRequestDto'

import { StateEnum } from '../enums/StateEnum'
import { GenreNameEnum } from '../enums/GenreNameEnum'
import { RoleNameEnum } from '../enums/RoleNameEnum'
import { SkillLevelEnum } from '../enums/SkillLevelEnum'
import { SexEnum } from '../enums/SexEnum'
import IUserLoginRequestDto from '../dto/IUserLoginRequestDto'

export interface IFormUserState {
    step: number;
    email: string;
    password: string;
    confirmPassword: string;
    name: string;
    sex: SexEnum;
    description: string;
    birthDate: string;
    state: StateEnum;
    city: string;
    likedGenres: GenreNameEnum[];
    roles: RoleNameEnum[];
    skillLevel: SkillLevelEnum;
}

export interface IListGenreName {
    size: number;
    genres: GenreNameEnum[];
}

export interface IListRoleName {
    size: number;
    roles: RoleNameEnum[];
}

const defaultUserValue: IFormUserState = {
    step: 1,
    email: '',
    password: '',
    confirmPassword: '',
    name: '',
    sex: SexEnum.NULL,
    description: '',
    birthDate: '',
    state: StateEnum.NULL,
    city: '',
    likedGenres: [],
    roles: [],
    skillLevel: SkillLevelEnum.NULL
}

const defaultListGenreNameValue: IListGenreName = {
    size: 0,
    genres: [] 
}

const defaultListRoleNameValue: IListRoleName = {
    size: 0,
    roles: []
}

export function MultiForm() {
    const navigate = useNavigate()

    const MySwal = withReactContent(Swal)

    const [userResponse, setUserResponse] = useState<IUserResponseDto>()
    const [formUserState, setFormState] = useState<IFormUserState>(defaultUserValue)

    const [listGenreName, setListGenreName] = useState<IListGenreName>(defaultListGenreNameValue)
    const [listRoleName, setListRoleName] = useState<IListRoleName>(defaultListRoleNameValue)
    
    const handleAddFromListChangeGenre = (value: GenreNameEnum) => {
        listGenreName.genres.push(value)
        listGenreName.size = listGenreName.size + 1
        setListGenreName(listGenreName)
        console.log(listGenreName)
    }

    const handleAddFromListChangeRole = (value: RoleNameEnum) => {
        listRoleName.roles.push(value)
        listRoleName.size = listRoleName.size + 1
        setListRoleName(listRoleName)
        console.log(listRoleName)
    }

    const handleRemoveFromListChangeGenre = (value: GenreNameEnum) => {
        const index = listGenreName.genres.indexOf(value)
        listGenreName.size = listGenreName.size - 1

        listGenreName.genres.splice(index, 1)

        setListGenreName(listGenreName)
        console.log(listGenreName)
    }

    const handleRemoveFromListChangeRole = (value: RoleNameEnum) => {
        const index = listRoleName.roles.indexOf(value)
        listRoleName.size = listRoleName.size - 1

        listRoleName.roles.splice(index, 1)

        setListRoleName(listRoleName)
        console.log(listRoleName)
    }

    const handleSaveFromListChange = () => {
        formUserState.likedGenres = listGenreName.genres
        formUserState.roles = listRoleName.roles

        console.log(listGenreName.genres)
        console.log(listRoleName.roles)
    }

    const handleFieldChange = (value: any, fieldName: string) => {
        setFormState({
            ...formUserState,
            [fieldName]: value
        })
    }

    const handleNextStep = () => {
        setFormState({
            ...formUserState,
            step: formUserState.step + 1
        })
    }

    const handlePreviousStep = () => {
        setFormState({
            ...formUserState,
            step: formUserState.step - 1
        })
    }

    const submitForm = () => {
        if (formUserState.password == formUserState.confirmPassword) {
            const user: IUserRequestDto = {
                email: formUserState.email,
                password: formUserState.password,
                name: formUserState.name,
                sex: formUserState.sex,
                description: formUserState.description,
                birthDate: formUserState.birthDate,
                state: formUserState.state,
                city: formUserState.city,
                likedGenres: formUserState.likedGenres,
                roles: formUserState.roles,
                skillLevel: formUserState.skillLevel
            }

            UserService.saveAndLogin(user).then(res => {
                if (res.status == 201) {
                    console.log(res.data)   

                    // alert('Usuário cadastrado com sucesso!')
                    Swal.fire('Usuário cadastrado com sucesso')
                    handleNextStep()
                }  
            }).catch(err => {
                console.log(err)
                Swal.fire('Erro ao cadastrar')
                // alert('Erro ao cadastrar')
            })

        } else {
            // alert('Senhas não conferem!')
            Swal.fire('Senhas não conferem')
            
        }
    }

    const signInUser = () => {
        const userLoginRequest: IUserLoginRequestDto = {
            email: formUserState.email,
            password: formUserState.password
        }

        console.log(userLoginRequest)

        UserService.login(userLoginRequest).then(res => {
            alert('logando')

            navigate(`/profile?id=${res.data.id}`)
        }).catch(err => {
            console.log(err)
            
            alert('erro ao tentar logar')
        })
    }

    const renderForms = () => {
        if (formUserState.step === 1) {
            if (formUserState)
            console.log(formUserState.step)
            return <Register nextStep={handleNextStep} handleChange={handleFieldChange} formState={formUserState} />
        } else 
        if (formUserState.step === 2) {
            console.log(formUserState.step)

            return <Register2 
                        submit={submitForm} 
                        listGenre={listGenreName} 
                        listRole={listRoleName} 
                        removeFromListGenre={handleRemoveFromListChangeGenre}
                        removeFromListRole={handleRemoveFromListChangeRole} 
                        addFromListGenre={handleAddFromListChangeGenre} 
                        addFromListRole={handleAddFromListChangeRole} 
                        saveList={handleSaveFromListChange}
                        previousStep={handlePreviousStep}
                        nextStep={handleNextStep} 
                        handleChange={handleFieldChange} 
                        formState={formUserState} 
                    />
        } else
        if (formUserState.step === 3) {
            console.log(formUserState.step)

            return <RegisterConcluid signIn={signInUser} />
        }

        return <></>
    }

    return (
        <>{renderForms()}</>
    )

}