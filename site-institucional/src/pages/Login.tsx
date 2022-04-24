import { useEffect, useState } from 'react'
import { Button, Form } from 'react-bootstrap'
import { useNavigate, Link } from 'react-router-dom'
import IUserResponseDto from '../dto/IUserResponseDto'
import IUserLoginRequestDto from '../dto/IUserLoginRequestDto'
import UserRoute from '../routes/UserRoute'

import { useForm, SubmitHandler } from "react-hook-form"

const defaultUserRequest: IUserLoginRequestDto = {
    email: '',
    password: ''
}

type FormValues = {
    email: string;
    password: string;
}

export function Login() {
    const navigate = useNavigate()

    const [user, setUser] = useState<IUserLoginRequestDto>(defaultUserRequest)
    const [userResponse, setUserResponse] = useState<IUserResponseDto>()

    const { register, handleSubmit } = useForm<IUserLoginRequestDto>()

    const onSubmit: SubmitHandler<IUserLoginRequestDto> = data => {
        UserRoute.login(data).then(res => {
            navigate(`/profile?id=${res.data.id}`)
        }).catch(error => {
            console.log(error)
        })
    }

    const handleFieldChange = (value: any, fieldName: string) => {
        setUser({
            ...user,
            [fieldName]: value
        })
    }

    return (
        <>
            <Form onSubmit={handleSubmit(onSubmit)}>
                <Form.Group controlId="formGroupEmail" className="form-group">
                    <Form.Control 
                        {...register("email")}
                        // onChange={(event) => handleFieldChange(event.target.value, 'email')} 
                        type="text" 
                        placeholder="E-mail" 
                        className="input-all form-label" 
                    />                                
                </Form.Group>
                <Form.Group controlId="formGroupPassword" className="form-group">
                    <Form.Control 
                        {...register("password")}
                        // onChange={(event) => handleFieldChange(event.target.value, 'password')} 
                        type="text" 
                        placeholder="Senha" 
                        className="input-all form-label" 
                    />                                
                </Form.Group>
                <Button type="submit" className="button">Entrar</Button>
            </Form>
        </>
    )
}