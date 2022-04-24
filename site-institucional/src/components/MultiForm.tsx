import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { useForm, SubmitHandler } from "react-hook-form"

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
    sex: SexEnum.NOT_KNOWN,
    description: '',
    birthDate: '',
    state: StateEnum.NULL,
    city: '',
    likedGenres: [GenreNameEnum.CLASSICAL],
    roles: [RoleNameEnum.BASSPLAYER],
    skillLevel: SkillLevelEnum.BEGINNER
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

    const [userResponse, setUserResponse] = useState<IUserResponseDto>()
    const [formUserState, setFormState] = useState<IFormUserState>(defaultUserValue)

    const [listGenreName, setListGenreName] = useState<IListGenreName>(defaultListGenreNameValue)
    const [listRoleName, setListRoleName] = useState<IListRoleName>(defaultListRoleNameValue)
    
    const handleAddFromListChange = (value: GenreNameEnum) => {
        listGenreName.genres.push(value)
        listGenreName.size = listGenreName.size + 1
        setListGenreName(listGenreName)
        console.log(listGenreName)
    }

    const handleRemoveFromListChange = (value: GenreNameEnum) => {
        const index = listGenreName.genres.indexOf(value)
        listGenreName.size = listGenreName.size - 1
        setListGenreName(listGenreName)
        console.log(listGenreName)
    }

    const handleSaveFromListChange = () => {
        formUserState.likedGenres = listGenreName.genres

        console.log(listGenreName.genres)
    }

    const handleSaveFromListChangeRole = () => {
        formUserState.roles = listRoleName.roles

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
                                 
                    const userLoginRequest: IUserLoginRequestDto = {
                        email: formUserState.email,
                        password: formUserState.password
                    }
        
                    UserService.login(userLoginRequest).then(res => {
                        alert('logando')
        
                        navigate(`/profile?id=${res.data.id}`)
                    }).catch(err => {
                        console.log(err)
                        alert('erro ao tentar logar')
                    })
                }  
            }).catch(err => {
                console.log(err)
                alert('Erro ao cadastrar')
            })

        } else {
            alert('Senhas não conferem!')
        }
    }

    const renderForms = () => {
        if (formUserState.step === 1) {
            if (formUserState)
            return <Register nextStep={handleNextStep} handleChange={handleFieldChange} formState={formUserState} />
        } else 
        if (formUserState.step === 2) {
            return <Register2 submit={submitForm} removeFromList={handleRemoveFromListChange} addFromListGenre={handleAddFromListChange} addFromListRole={handleSaveFromListChange} saveList={handleSaveFromListChange} previousStep={handlePreviousStep} nextStep={handleNextStep} handleChange={handleFieldChange} formState={formUserState} />
        }

        return <></>
    }

    return (
        <>{renderForms()}</>
    )

}