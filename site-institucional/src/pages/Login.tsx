import { Button, Form } from 'react-bootstrap'
import { useNavigate, Link } from 'react-router-dom'
import IUserLoginRequestDto from '../dto/IUserLoginRequestDto'
import UserRoute from '../routes/UserRoute'

import { useForm, SubmitHandler } from "react-hook-form"

import '../styles/login.css'

export function Login() {
    const navigate = useNavigate()

    const { register, handleSubmit } = useForm<IUserLoginRequestDto>()

    const onSubmit: SubmitHandler<IUserLoginRequestDto> = data => {
        UserRoute.login(data).then(res => {
            navigate(`/profile?id=${res.data.id}`)
        }).catch(error => {
            console.log(error)
        })
    }

    const navigateToHome = () => {
        navigate('/')
    }

    return (
        <div className="page-login">
            <section className="login-container">
                <i className='bx bx-right-arrow-alt fechar' onClick={navigateToHome}></i>
                <div className="titulo">
                    <h1>Entrar</h1>
                </div>
                <div className="corpo">

                    <div className="dados">
                        <Form onSubmit={handleSubmit(onSubmit)}>
                            <Form.Group controlId="formGroupEmail">
                                <Form.Control 
                                    {...register("email")}
                                    type="text" 
                                    placeholder="E-mail" 
                                    className="campo input-all" 
                                />                                
                            </Form.Group>
                            <Form.Group controlId="formGroupPassword">
                                <Form.Control 
                                    {...register("password")}
                                    type="password" 
                                    placeholder="Senha" 
                                    className="campo input-all"
                                />                                
                            </Form.Group>
                            <Button type="submit" className="button2">Entrar</Button>
                        </Form>
                    </div>

                </div>
            </section>
        </div>
    )
}