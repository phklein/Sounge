
import { Button, Form } from 'react-bootstrap'
import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'

import '../../styles/register.css'

import UserService from '../../services/ArtistService'

import { Buttonaa } from '../../components/Buttonaa'
import { IFormState } from '../../components/MultiForm'

import { StateEnum } from '../../enums/StateEnum'



interface Iprops {
    nextStep: () => void;
    formState: IFormState;
    handleChange: (value: any, fieldName: string) => void;
}

export function Register(props: Iprops) {
    const navigate = useNavigate()
    
    const {nextStep, formState, handleChange} = props;

    const cancelRegister = () => {
        navigate('/')
    }

    return (
        <>
            <div className="max-width-height">
                <div className="register-container-left">
                    <Form>
                        <div className="register-form">
                            <h2>Crie sua conta</h2>
                            <Form.Group controlId="formGroupNome">
                                <Form.Control 
                                    value={formState.nome} 
                                    onChange={(event) => handleChange(event.target.value, 'nome')}
                                    type="text" 
                                    placeholder="Nome" 
                                    className="input-all form-label"                               
                                />
                            </Form.Group>
                            <div className="container-input-hlf">
                                <Form.Group controlId="formGroupEmail" className="form-group">
                                    <Form.Control 
                                        value={formState.email} 
                                        onChange={(event) => handleChange(event.target.value, 'email')}
                                        type="text" 
                                        placeholder="E-mail" 
                                        className="input-all form-label"
                                    />                                
                                </Form.Group>
                                <Form.Group controlId="formGroupCpf" className="form-group">
                                    <Form.Control 
                                        value={formState.cpf} 
                                        onChange={(event) => handleChange(event.target.value, 'cpf')}
                                        type="text" 
                                        placeholder="CPF" 
                                        className="input-all form-label"
                                    />                                
                                </Form.Group>
                            </div>
                            <div className="container-input-hlf">
                                <Form.Group controlId="formGroupEstado" className="form-group">
                                    <Form.Select value={formState.estado} onChange={(event) => handleChange(event.target.value, 'estado')} className="form-select">
                                        <option value={StateEnum.NULL}>Selecione o seu Estado</option>
                                        <option value={StateEnum.SP}>{StateEnum.SP.valueOf}</option>
                                        <option value={StateEnum.RJ}>{StateEnum.RJ.valueOf}</option>
                                        <option value={StateEnum.MG}>{StateEnum.MG.valueOf}</option>
                                        <option value={StateEnum.PR}>{StateEnum.PR.valueOf}</option>
                                        <option value={StateEnum.RS}>{StateEnum.RS.valueOf}</option>
                                        <option value={StateEnum.SC}>{StateEnum.SC.valueOf}</option>
                                    </Form.Select>
                                </Form.Group>
                                <Form.Group controlId="formGroupCidade" className="form-group">
                                    <Form.Control 
                                        value={formState.cidade} 
                                        onChange={(event) => handleChange(event.target.value, 'cidade')}
                                        type="text" 
                                        placeholder="Cidade" 
                                        className="input-all form-label" 
                                    />                                
                                </Form.Group>
                            </div>
                            <Form.Group controlId="formGroupSenha">
                                <Form.Control 
                                    value={formState.senha} 
                                    onChange={(event) => handleChange(event.target.value, 'senha')}
                                    type="text" 
                                    placeholder="Senha" 
                                    className="input-all form-label"
                                />                            
                                </Form.Group>
                            <Form.Group controlId="formGroupConfirmarSenha">
                                <Form.Control 
                                    value={formState.confirmarSenha} 
                                    onChange={(event) => handleChange(event.target.value, 'confirmarSenha')}
                                    type="text" 
                                    placeholder="Confirmar Senha" 
                                    className="input-all form-label"
                                />                            
                            </Form.Group>
                        </div>
                        <Form.Group className="form-group-previous">
                            <Button onClick={cancelRegister} className="button">Cancelar</Button>
                        </Form.Group>
                        <Form.Group className="form-group-next">
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