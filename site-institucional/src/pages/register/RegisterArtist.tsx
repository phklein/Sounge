import { useState } from 'react'
import { Button, Form } from 'react-bootstrap'

import '../../styles/register.css'

import { IFormState } from '../../components/MultiForm'

interface Iprops {
    nextStep: () => void;
    formState: IFormState;
    handleChange: (value: any, fieldName: string) => void;
    previousStep: () => void;
}

export function RegisterArtist(props: Iprops) {
    const { nextStep, formState, handleChange, previousStep } = props;

    return (
        <>
            <div className="max-width-height">
                <div className="register-container-left">
                    <Form>
                        <div className="register-form">
                            <h2>bbbbbbbbbbb</h2>
                            {/* <Form.Group controlId="formGroupEstiloMusical">
                                <Form.Label />
                                <Form.Control 
                                    value={formState.estiloMusical} 
                                    onChange={(event) => handleChange(event.target.value, 'estiloMusical')}
                                    type="text" 
                                    placeholder="Estilo Musical" 
                                    className="input-all"
                                />
                            </Form.Group>
                            <Form.Group controlId="formGroupCategoriaMusicas">
                                <Form.Label />
                                <Form.Control 
                                    value={formState.categoriaMusicas} 
                                    onChange={(event) => handleChange(event.target.value, 'categoriaMusicas')}
                                    type="text" 
                                    placeholder="Categoria de Músicas" 
                                    className="input-all"
                                />
                            </Form.Group> */}
                            <Form.Group controlId="formGroupNivel">
                                <Form.Control 
                                    value={formState.nivel} 
                                    onChange={(event) => handleChange(event.target.value, 'nivel')}
                                    type="text" 
                                    placeholder="Nível" 
                                    className="input-all form-label"
                                />
                            </Form.Group>
                        </div>
                        <Form.Group className="form-group-previous">
                            <Button onClick={previousStep} type="submit" className="button">Voltar</Button>
                        </Form.Group>
                        <Form.Group className="form-group-next">
                            <Button onClick={nextStep} type="submit" className="button">Finalizar</Button>
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