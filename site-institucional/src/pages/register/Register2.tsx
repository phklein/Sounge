import { useState } from 'react'
import { Button, Form, Modal } from 'react-bootstrap'
import { useForm, SubmitHandler } from "react-hook-form"

import '../../styles/register.css'

import IUserRequestDto from '../../dto/IUserRequestDto'

import { IFormUserState } from '../../components/MultiForm'

import { GenreNameEnum, GenreNameEnumDesc } from '../../enums/GenreNameEnum'
import { RoleNameEnum, RoleNameEnumDesc } from '../../enums/RoleNameEnum'
import { SkillLevelEnum, SkillLevelEnumDesc } from '../../enums/SkillLevelEnum'

interface Iprops {
    formState: IFormUserState;
    nextStep: () => void;
    handleChange: (value: any, fieldName: string) => void;
    previousStep: () => void;
    addFromListGenre: (value: GenreNameEnum) => void;
    addFromListRole: (value: RoleNameEnum) => void;
    removeFromList: (value: GenreNameEnum) => void;
    saveList: () => void;
    submit: () => void;
}

export function Register2(props: Iprops) {
    
    const { nextStep, formState, handleChange, previousStep, addFromListGenre, addFromListRole, saveList, submit } = props
    const [show, setShow] = useState(false)
    
    const { handleSubmit } = useForm<IUserRequestDto>()

    const onSubmit: SubmitHandler<IUserRequestDto> = data => {
        props.submit()
    }
    
    const handleClose = () => {
        setShow(false)
    }

    const handleShow = (test: string) => {
        if (test === 'a') {
            setShow(true)
        } else if (test === 'b') {
            setShow(true)
        }
    }
  
    return (
        <>
            <div className="max-width-height">
                <div className="register-container-left">
                    <Form onSubmit={handleSubmit(onSubmit)}>
                        <div className="register-form">
                            <h2>Fale um pouco mais sobre você</h2>
                            <div className="form-group-modal">
                                <p>Adicionar gênero musical</p> 
                                <i onClick={(event) => handleShow('a')} className='bx bx-x'></i>                        
                            </div>
                            <div className="form-group-modal">
                                <p>Adicionar Instrumento</p> 
                                <i onClick={(event) => handleShow('b')} className='bx bx-x'></i>                        
                            </div>
                            <Modal 
                                className="modal"
                                show={show}
                                onHide={handleClose}
                                size="lg"
                                aria-labelledby="contained-modal-title-vcenter"
                                centered
                            >
                                <Modal.Header>
                                    <Modal.Title className="modal-title">Gêneros Musicais</Modal.Title>
                                </Modal.Header>
                                <Modal.Body className="modal-body">
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.CLASSICAL)}>{GenreNameEnumDesc.get(GenreNameEnum.CLASSICAL)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.ELECTRONIC)}>{GenreNameEnumDesc.get(GenreNameEnum.ELECTRONIC)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.FUNK)}>{GenreNameEnumDesc.get(GenreNameEnum.FUNK)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.METAL)}>{GenreNameEnumDesc.get(GenreNameEnum.METAL)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.POP)}>{GenreNameEnumDesc.get(GenreNameEnum.POP)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.ROCK)}>{GenreNameEnumDesc.get(GenreNameEnum.ROCK)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.PAGODE)}>{GenreNameEnumDesc.get(GenreNameEnum.PAGODE)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.FORRO)}>{GenreNameEnumDesc.get(GenreNameEnum.FORRO)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.INDIE)}>{GenreNameEnumDesc.get(GenreNameEnum.INDIE)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.RAP)}>{GenreNameEnumDesc.get(GenreNameEnum.RAP)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.MPB)}>{GenreNameEnumDesc.get(GenreNameEnum.MPB)} <i className='bx bx-x'></i></div>
                                </Modal.Body>
                                <Modal.Footer>
                                    <Form.Group className="form-group-next">
                                        <Button className="btn-save-modal" variant="primary" type="submit" onClick={saveList}>Salvar</Button>
                                    </Form.Group>
                                </Modal.Footer>
                            </Modal>
                            <Modal 
                                className="modal"
                                show={show}
                                onHide={handleClose}
                                size="lg"
                                aria-labelledby="contained-modal-title-vcenter"
                                centered
                            >
                                <Modal.Header>
                                    <Modal.Title className="modal-title">Instrumentos</Modal.Title>
                                </Modal.Header>
                                <Modal.Body className="modal-body">
                                    <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.ACCORDIONIST)}>{RoleNameEnumDesc.get(RoleNameEnum.ACCORDIONIST)} <i className='bx bx-x'></i></div>
                                </Modal.Body>
                                <Modal.Footer>
                                    <Form.Group className="form-group-next">
                                        <Button className="btn-save-modal" variant="primary" type="submit" onClick={saveList}>Salvar</Button>
                                    </Form.Group>
                                </Modal.Footer>
                            </Modal>
                            <Form.Group controlId="formGroupSkillLevel" className="form-group-all">
                                <Form.Select value={formState.skillLevel} onChange={(event) => handleChange(event.target.value, 'skillLevel')} className="form-select">
                                    <option value="NULL">Selecione o seu Nível</option>
                                    <option value={SkillLevelEnum.BEGINNER}>{SkillLevelEnumDesc.get(SkillLevelEnum.BEGINNER)}</option>
                                    <option value={SkillLevelEnum.INTERMEDIATE}>{SkillLevelEnumDesc.get(SkillLevelEnum.INTERMEDIATE)}</option>
                                    <option value={SkillLevelEnum.ADVANCED}>{SkillLevelEnumDesc.get(SkillLevelEnum.ADVANCED)}</option>
                                    <option value={SkillLevelEnum.EXPERT}>{SkillLevelEnumDesc.get(SkillLevelEnum.EXPERT)}</option>
                                </Form.Select>                          
                            </Form.Group>
                            <Form.Group controlId="formGroupBirthDate" className="form-group-all">
                                <Form.Control 
                                    value={formState.birthDate} 
                                    onChange={(event) => handleChange(event.target.value, 'birthDate')}
                                    type="date" 
                                    placeholder="Data de nascimento" 
                                    className="input-all form-label"
                                />                                
                            </Form.Group>
                            <Form.Group controlId="formGroupDescription" className="form-group-all">
                                <Form.Control 
                                    value={formState.description} 
                                    onChange={(event) => handleChange(event.target.value, 'description')}
                                    type="text" 
                                    placeholder="Descrição" 
                                    className="input-all form-label"
                                />                                
                            </Form.Group>
                        </div>
                        <Form.Group className="form-group-previous">
                            <Button onClick={previousStep} type="submit" className="button">Voltar</Button>
                        </Form.Group>
                        <Form.Group className="form-group-next">
                            <Button type="submit" className="button">Finalizar</Button>
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