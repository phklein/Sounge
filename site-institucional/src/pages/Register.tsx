
import { Button, Form } from 'react-bootstrap'
import { useState } from 'react'
import UserService from '../services/UserMusicianService'
import { useNavigate, Link } from 'react-router-dom'

import { Buttonaa } from '../components/Buttonaa'
import { IFormState } from '../components/MultiForm'

import '../styles/register.css'

interface Iprops {
    nextStep: () => void;
    formState: IFormState;
    handleChange: (value: string, fieldName: string) => void;
}

export function Register(props: Iprops) {
    const {nextStep, formState, handleChange} = props;

    return (
        <>
            <div className="max-width-height">
                <div className="register-container-left">
                    <Form>
                        <div className="register-form">
                            <h2>Crie sua conta</h2>
                            <Form.Group controlId="formGroupNome">
                                <Form.Label />
                                <Form.Control 
                                    value={formState.nome} 
                                    onChange={(event) => handleChange(event.target.value, 'nome')}
                                    type="text" 
                                    placeholder="Nome" 
                                    className="input-all"
                                />
                            </Form.Group>
                            <div className="container-input-hlf">
                                <Form.Group controlId="formGroupEmail">
                                    <Form.Label />
                                    <Form.Control 
                                        value={formState.email} 
                                        onChange={(event) => handleChange(event.target.value, 'email')}
                                        type="text" 
                                        placeholder="E-mail" 
                                        className="input-all"
                                    />                                
                                </Form.Group>
                                <Form.Group controlId="formGroupCpf">
                                    <Form.Label />
                                    <Form.Control 
                                        value={formState.cpf} 
                                        onChange={(event) => handleChange(event.target.value, 'cpf')}
                                        type="text" 
                                        placeholder="CPF" 
                                        className="input-all"
                                    />                                
                                </Form.Group>
                            </div>
                            <div className="container-input-hlf">
                                <Form.Group controlId="formGroupEstado">
                                    <Form.Label />
                                    <Form.Control 
                                        value={formState.estado} 
                                        onChange={(event) => handleChange(event.target.value, 'estado')}
                                        type="text" 
                                        placeholder="Estado" 
                                        className="input-all"
                                    />                                
                                </Form.Group>
                                <Form.Group controlId="formGroupCidade">
                                    <Form.Label />
                                    <Form.Control 
                                        value={formState.cidade} 
                                        onChange={(event) => handleChange(event.target.value, 'cidade')}
                                        type="text" 
                                        placeholder="Cidade" 
                                        className="input-all"
                                    />                                
                                </Form.Group>
                            </div>
                            <Form.Group controlId="formGroupSenha">
                                <Form.Label />
                                <Form.Control 
                                    value={formState.senha} 
                                    onChange={(event) => handleChange(event.target.value, 'senha')}
                                    type="text" 
                                    placeholder="Senha" 
                                    className="input-all"
                                />                            
                                </Form.Group>
                            <Form.Group controlId="formGroupConfirmarSenha">
                                <Form.Label />
                                <Form.Control 
                                    value={formState.confirmarSenha} 
                                    onChange={(event) => handleChange(event.target.value, 'confirmarSenha')}
                                    type="text" 
                                    placeholder="Confirmar Senha" 
                                    className="input-all"
                                />                            
                            </Form.Group>
                        </div>
                        <Buttonaa type="button">Cancelar</Buttonaa>
                        <Form.Group>
                            <Button onClick={nextStep} type="submit" className="button">Próximo</Button>
                        </Form.Group>
                    </Form>
                </div>
                <div className="register-container-right">
                    <img src="https://s3-alpha-sig.figma.com/img/e68c/cd7e/cf67a34866a0ad05813a407693875fe9?Expires=1650240000&Signature=E-O1wX8pmHh~GNUyuTcBbpX6HVwjrSN7CJcCe7AUNxIP818-y-CtQallqQj7M-CdCB6ZIXU4wnBJeO0FOOS9GytnaOwPd5abRrVWrvqfzr-hqBCR26W-UZ8Ha09dbjy2ZopOBOCSbA9zY1Xk5s~zYdODXBVg9hy-l4ZV9oKmGeFMiEZL5Xz8MmoInwEstgeZC-8Qa8uh9NACf-t-mnpZFsTlVZ1ZP6ckb1MONrBp16iW-616K6Msl0pw12YTlZ9LeQjK6s8rbCPw9K9H3ffDIYWQCwXBrr8~7b7XZx9s6BxXf9Gl8JL0JZc5a45O2npl9vN3xZSkvHWUbJgQ4M~-pw__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA" alt="" />
                    <h1>Crie sua conta<br />aqui agora!</h1>
                    <p>Lorem ipsum dolor sit amet, consectetur incididunt ut labore et dolore magna aliqua.</p>
                </div>
            </div>
        </>
    )
}