import { useEffect, useState } from 'react'
import { Button, Form, Modal } from 'react-bootstrap'
import { useForm, SubmitHandler } from "react-hook-form"

import '../../styles/register.css'

import LogoHorizontal from '../../assets/img/logo-fundo-escuro-texto-horizontal.png'

import { IFormUserState, IListGenreName, IListRoleName } from '../../components/MultiForm'

import IUserRequestDto from '../../dto/IUserRequestDto'

import { GenreNameEnum, GenreNameEnumDesc } from '../../enums/GenreNameEnum'
import { RoleNameEnum, RoleNameEnumDesc } from '../../enums/RoleNameEnum'
import { SkillLevelEnum, SkillLevelEnumDesc } from '../../enums/SkillLevelEnum'

interface Iprops {
    formState: IFormUserState;
    listGenre: IListGenreName;
    listRole: IListRoleName;
    nextStep: () => void;
    handleChange: (value: any, fieldName: string) => void;
    previousStep: () => void;
    addFromListGenre: (value: GenreNameEnum) => void;
    addFromListRole: (value: RoleNameEnum) => void;
    saveList: () => void;
    submit: () => void;
    removeFromListGenre: (value: GenreNameEnum) => void;
    removeFromListRole: (value: RoleNameEnum) => void;
}

export function Register2(props: Iprops) {
    const { nextStep, formState, listGenre, listRole, removeFromListGenre, removeFromListRole, handleChange, previousStep, addFromListGenre, addFromListRole, saveList, submit } = props
    
    const [showRole, setShowRole] = useState(false)
    const [showGenre, setShowGenre] = useState(false)
    const [genresNotSelected, setGenresNotSelected] = useState([] as GenreNameEnum[])
    const [rolesNotSelected, setRolesNotSelected] = useState([] as RoleNameEnum[])

    useEffect(() => {
        genresNotSelected.push(GenreNameEnum.CLASSICAL)
        genresNotSelected.push(GenreNameEnum.ROCK)
        genresNotSelected.push(GenreNameEnum.POP)
        genresNotSelected.push(GenreNameEnum.RAB)
        genresNotSelected.push(GenreNameEnum.METAL)
        genresNotSelected.push(GenreNameEnum.TRAP)
        genresNotSelected.push(GenreNameEnum.KPOP)
        genresNotSelected.push(GenreNameEnum.LOFI)
        genresNotSelected.push(GenreNameEnum.INDIE)
        genresNotSelected.push(GenreNameEnum.RAP)
        genresNotSelected.push(GenreNameEnum.ELECTRONIC)
        genresNotSelected.push(GenreNameEnum.FUNK)
        genresNotSelected.push(GenreNameEnum.MPB)
        genresNotSelected.push(GenreNameEnum.SERTANEJO)
        genresNotSelected.push(GenreNameEnum.PAGODE)
        genresNotSelected.push(GenreNameEnum.FORRO)

        rolesNotSelected.push(RoleNameEnum.GUITARIST)
        rolesNotSelected.push(RoleNameEnum.ACCORDIONIST)
        rolesNotSelected.push(RoleNameEnum.EGUITARIST)
        rolesNotSelected.push(RoleNameEnum.VIOLINIST)
        rolesNotSelected.push(RoleNameEnum.BASSPLAYER)
        rolesNotSelected.push(RoleNameEnum.UKULELEPLAYER)
        rolesNotSelected.push(RoleNameEnum.DRUMMER)
        rolesNotSelected.push(RoleNameEnum.TAMBOURINEPLAYER)
        rolesNotSelected.push(RoleNameEnum.PIANIST)
        rolesNotSelected.push(RoleNameEnum.EKEYBOARDPLAYER)
        rolesNotSelected.push(RoleNameEnum.ACCORDIONIST)
        rolesNotSelected.push(RoleNameEnum.CORNETPLAYER)
        rolesNotSelected.push(RoleNameEnum.TROMBONIST)
        rolesNotSelected.push(RoleNameEnum.SAXOPHONIST)
        rolesNotSelected.push(RoleNameEnum.FLUTIST)
        rolesNotSelected.push(RoleNameEnum.DJ)
        rolesNotSelected.push(RoleNameEnum.PRODUCER)
        rolesNotSelected.push(RoleNameEnum.VOCALIST)
        rolesNotSelected.push(RoleNameEnum.OTHERS)
    }, [])

    
    const { handleSubmit } = useForm<IUserRequestDto>()
    
    const onSubmit: SubmitHandler<IUserRequestDto> = data => {
        props.submit()
    }
    
    const handleClose = () => {
        setShowGenre(false)
        setShowRole(false)
    }

    const handleShow = (type: string) => {
        if (type === 'genre') {
            setShowGenre(true)
        } else if (type === 'role') {
            setShowRole(true)
        }
    }

    const execute = () => {
        props.saveList()

        // genresNotSelected.forEach(genre => {
        //     genresNotSelected.splice(listGenre.genres.indexOf(genre), 1)
        //     setGenresNotSelected([...genresNotSelected])
        // })
        
        // rolesNotSelected.forEach(role => {
        //     rolesNotSelected.splice(listRole.roles.indexOf(role), 1)
        //     setRolesNotSelected([...rolesNotSelected])
        // })

        handleClose()
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
                                <i onClick={(event) => handleShow('genre')} className='bx bx-x'></i>                     
                            </div>
                            <div className="form-group-modal">
                                <p>Adicionar Instrumento</p> 
                                <i onClick={(event) => handleShow('role')} className='bx bx-x'></i>                        
                            </div>
                            <Modal 
                                className="modal"
                                show={showGenre}
                                onHide={handleClose}
                                size="lg"
                                aria-labelledby="contained-modal-title-vcenter"
                                centered
                            >
                                <Modal.Header>
                                    <Modal.Title className="modal-title">Gêneros Musicais</Modal.Title>
                                </Modal.Header>
                                <Modal.Body className="modal-body">
                                    {
                                        genresNotSelected.map(genre => {
                                            return (
                                                <div className="option-genre" onClick={(event) => addFromListGenre(genre)}>{GenreNameEnumDesc.get(genre)} <i className='bx bx-x'></i></div>
                                            )
                                        })
                                    }
                                    {/* <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.ROCK)}>{GenreNameEnumDesc.get(GenreNameEnum.ROCK)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.METAL)}>{GenreNameEnumDesc.get(GenreNameEnum.METAL)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.POP)}>{GenreNameEnumDesc.get(GenreNameEnum.POP)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.RAP)}>{GenreNameEnumDesc.get(GenreNameEnum.RAP)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.CLASSICAL)}>{GenreNameEnumDesc.get(GenreNameEnum.CLASSICAL)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.LOFI)}>{GenreNameEnumDesc.get(GenreNameEnum.LOFI)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.RAB)}>{GenreNameEnumDesc.get(GenreNameEnum.RAB)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.TRAP)}>{GenreNameEnumDesc.get(GenreNameEnum.TRAP)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.KPOP)}>{GenreNameEnumDesc.get(GenreNameEnum.KPOP)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.INDIE)}>{GenreNameEnumDesc.get(GenreNameEnum.INDIE)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.ELECTRONIC)}>{GenreNameEnumDesc.get(GenreNameEnum.ELECTRONIC)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.FUNK)}>{GenreNameEnumDesc.get(GenreNameEnum.FUNK)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListGenre(GenreNameEnum.MPB)}>{GenreNameEnumDesc.get(GenreNameEnum.MPB)} <i className='bx bx-x'></i></div> */}
                                </Modal.Body>
                                <hr className='hr'/>
                                <Modal.Body className="modal-body">
                                    {
                                        listGenre.genres.map(genre => {
                                            return (
                                                <div className="option-genre-select" onClick={(event) => removeFromListGenre(genre)}>{GenreNameEnumDesc.get(genre)} <i className='bx bx-check'></i></div>
                                            )
                                        })
                                    }
                                </Modal.Body>
                                <Modal.Footer className="container-button-modal">                                    
                                    <Form.Group className="form-group-previous">
                                        <Button variant="secondary" className="btn-close-modal" onClick={handleClose}>Fechar</Button>
                                    </Form.Group>
                                    <Form.Group className="form-group-next">
                                        <Button className="btn-save-modal" variant="primary" type="submit" onClick={execute}>Salvar</Button>
                                    </Form.Group>
                                </Modal.Footer>
                            </Modal>
                            <Modal 
                                className="modal"
                                show={showRole}
                                onHide={handleClose}
                                size="lg"
                                aria-labelledby="contained-modal-title-vcenter"
                                centered
                            >
                                <Modal.Header>
                                    <Modal.Title className="modal-title">Instrumentos</Modal.Title>
                                </Modal.Header>
                                <Modal.Body className="modal-body">
                                    {
                                        rolesNotSelected.map(role => {
                                            return (
                                                <div className="option-genre" onClick={(event) => addFromListRole(role)}>{RoleNameEnumDesc.get(role)} <i className='bx bx-x'></i></div>
                                            )
                                        })
                                    }
                                    {/* <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.GUITARIST)}>{RoleNameEnumDesc.get(RoleNameEnum.GUITARIST)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.EGUITARIST)}>{RoleNameEnumDesc.get(RoleNameEnum.EGUITARIST)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.VIOLINIST)}>{RoleNameEnumDesc.get(RoleNameEnum.VIOLINIST)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.BASSPLAYER)}>{RoleNameEnumDesc.get(RoleNameEnum.BASSPLAYER)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.UKULELEPLAYER)}>{RoleNameEnumDesc.get(RoleNameEnum.UKULELEPLAYER)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.DRUMMER)}>{RoleNameEnumDesc.get(RoleNameEnum.DRUMMER)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.TAMBOURINEPLAYER)}>{RoleNameEnumDesc.get(RoleNameEnum.TAMBOURINEPLAYER)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.PIANIST)}>{RoleNameEnumDesc.get(RoleNameEnum.PIANIST)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.EKEYBOARDPLAYER)}>{RoleNameEnumDesc.get(RoleNameEnum.EKEYBOARDPLAYER)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.ACCORDIONIST)}>{RoleNameEnumDesc.get(RoleNameEnum.ACCORDIONIST)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.CORNETPLAYER)}>{RoleNameEnumDesc.get(RoleNameEnum.CORNETPLAYER)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.TROMBONIST)}>{RoleNameEnumDesc.get(RoleNameEnum.TROMBONIST)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.SAXOPHONIST)}>{RoleNameEnumDesc.get(RoleNameEnum.SAXOPHONIST)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.FLUTIST)}>{RoleNameEnumDesc.get(RoleNameEnum.FLUTIST)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.DJ)}>{RoleNameEnumDesc.get(RoleNameEnum.DJ)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.PRODUCER)}>{RoleNameEnumDesc.get(RoleNameEnum.PRODUCER)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.VOCALIST)}>{RoleNameEnumDesc.get(RoleNameEnum.VOCALIST)} <i className='bx bx-x'></i></div>
                                    <div className="option-genre" onClick={(event) => addFromListRole(RoleNameEnum.OTHERS)}>{RoleNameEnumDesc.get(RoleNameEnum.OTHERS)} <i className='bx bx-x'></i></div> */}
                                </Modal.Body>
                                <hr className='hr'/>
                                <Modal.Body className="modal-body">
                                    {
                                        listRole.roles.map(role => {
                                            return (
                                                <div className="option-genre-select" onClick={(event) => removeFromListRole(role)}>{RoleNameEnumDesc.get(role)} <i className='bx bx-check'></i></div>
                                            )
                                        })
                                    }
                                </Modal.Body>
                                <Modal.Footer>
                                    <Form.Group className="form-group-previous">
                                        <Button variant="secondary" className="btn-close-modal" onClick={handleClose}>Fechar</Button>
                                    </Form.Group>
                                    <Form.Group className="form-group-next">
                                        <Button className="btn-save-modal" variant="primary" type="submit" onClick={execute}>Salvar</Button>
                                    </Form.Group>
                                </Modal.Footer>
                            </Modal>
                            <Form.Group controlId="formGroupSkillLevel" className="form-group-all">
                                <Form.Select value={formState.skillLevel} onChange={(event) => handleChange(event.target.value, 'skillLevel')} className="form-select">
                                    <option value={SkillLevelEnum.NULL}>Selecione o seu Nível</option>
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
                    <img src={LogoHorizontal} alt="" />
                    <h1>Crie sua conta<br />aqui agora!</h1>
                    <p>Tocar uma nota equivocada é insignificante. Tocar sem paixão é imperdoável</p>
                </div>
            </div>
        </>
    )
}