import { useEffect, useState } from "react"
import { Button, Form, Modal } from "react-bootstrap"
import { useForm, SubmitHandler } from "react-hook-form"

import "../../styles/register.css"

import LogoHorizontal from "../../assets/img/logo-fundo-escuro-texto-horizontal.png"

import { IFormUserState, IListGenreName, IListRoleName } from "../../components/MultiForm"

import IUserRequestDto from "../../dto/request/UserRequestDto"

import { GenreNameEnum, GenreNameEnumDesc } from "../../enums/GenreNameEnum"
import { RoleNameEnum, RoleNameEnumDesc } from "../../enums/RoleNameEnum"
import { SkillLevelEnum, SkillLevelEnumDesc } from "../../enums/SkillLevelEnum"

interface Iprops {
	formState: IFormUserState
	listGenre: IListGenreName
	listRole: IListRoleName
	nextStep: () => void
	handleChange: (value: any, fieldName: string) => void
	previousStep: () => void
	addFromListGenre: (value: GenreNameEnum) => void
	addFromListRole: (value: RoleNameEnum) => void
	saveList: () => void
	submit: () => void
	removeFromListGenre: (value: GenreNameEnum) => void
	removeFromListRole: (value: RoleNameEnum) => void
}

export function Register2(props: Iprops) {
	const {
		nextStep,
		formState,
		listGenre,
		listRole,
		removeFromListGenre,
		removeFromListRole,
		handleChange,
		previousStep,
		addFromListGenre,
		addFromListRole,
		saveList,
		submit,
	} = props

	const [showRole, setShowRole] = useState(false)
	const [showGenre, setShowGenre] = useState(false)
	const [genresNotSelected, setGenresNotSelected] = useState([] as GenreNameEnum[])
	const [rolesNotSelected, setRolesNotSelected] = useState([] as RoleNameEnum[])

	useEffect(() => {
		const newGenresNotSelected = [
			GenreNameEnum.CLASSICAL,
			GenreNameEnum.ROCK,
			GenreNameEnum.POP,
			GenreNameEnum.RAB,
			GenreNameEnum.METAL,
			GenreNameEnum.TRAP,
			GenreNameEnum.KPOP,
			GenreNameEnum.LOFI,
			GenreNameEnum.INDIE,
			GenreNameEnum.RAP,
			GenreNameEnum.ELECTRONIC,
			GenreNameEnum.FUNK,
			GenreNameEnum.MPB,
			GenreNameEnum.SERTANEJO,
			GenreNameEnum.PAGODE,
			GenreNameEnum.FORRO,
		]
		const newRolesNotSelected = [
			RoleNameEnum.GUITARIST,
			RoleNameEnum.ACCORDIONIST,
			RoleNameEnum.EGUITARIST,
			RoleNameEnum.VIOLINIST,
			RoleNameEnum.BASSPLAYER,
			RoleNameEnum.UKULELEPLAYER,
			RoleNameEnum.DRUMMER,
			RoleNameEnum.TAMBOURINEPLAYER,
			RoleNameEnum.PIANIST,
			RoleNameEnum.EKEYBOARDPLAYER,
			RoleNameEnum.ACCORDIONIST,
			RoleNameEnum.CORNETPLAYER,
			RoleNameEnum.TROMBONIST,
			RoleNameEnum.SAXOPHONIST,
			RoleNameEnum.FLUTIST,
			RoleNameEnum.DJ,
			RoleNameEnum.PRODUCER,
			RoleNameEnum.VOCALIST,
			RoleNameEnum.OTHERS,
		]
		setGenresNotSelected(newGenresNotSelected)
		setRolesNotSelected(newRolesNotSelected)
	}, [])

	const { handleSubmit } = useForm<IUserRequestDto>()

	const onSubmit: SubmitHandler<IUserRequestDto> = (data) => {
		props.submit()
	}

	const handleClose = () => {
		setShowGenre(false)
		setShowRole(false)
	}

	const handleShow = (type: string) => {
        handleClose()
		if (type === "genre") {
			setShowGenre(true)
		} else if (type === "role") {
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
			<div className='max-width-height'>
				<div className='register-container-left'>
					<Form onSubmit={handleSubmit(onSubmit)}>
						<div className='register-form'>
							<h2>Fale um pouco mais sobre você</h2>
							<button className='form-group-modal' type="button" onClick={() => handleShow("genre")}>
								<p>Adicionar gênero musical</p>
								<i className='bx bx-x'></i>
							</button>
							<button className='form-group-modal' type="button" onClick={() => handleShow("role")}>
								<p>Adicionar Instrumento</p>
								<i className='bx bx-x'></i>
							</button>
							<Modal
								className='modal'
								show={showGenre}
								onHide={handleClose}
								size='lg'
								aria-labelledby='contained-modal-title-vcenter'
								centered
							>
								<Modal.Header>
									<Modal.Title className='modal-title'>Gêneros Musicais</Modal.Title>
								</Modal.Header>
								<Modal.Body className='modal-body'>
									{genresNotSelected.map((genre) => {
										return (
											<div
												className='option-genre'
												onClick={() => {
													addFromListGenre(genre)
													setGenresNotSelected(
														genresNotSelected.filter((genreNotSelected) => genreNotSelected !== genre)
													)
												}}
											>
												{GenreNameEnumDesc.get(genre)} <i className='bx bx-x'></i>
											</div>
										)
									})}
								</Modal.Body>
								<hr className='hr' />
								<Modal.Body className='modal-body'>
									{listGenre.genres.map((genre) => {
										return (
											<div
												className='option-genre-select'
												onClick={() => {
													removeFromListGenre(genre)
													setGenresNotSelected([...genresNotSelected, genre])
												}}
											>
												{GenreNameEnumDesc.get(genre)} <i className='bx bx-check'></i>
											</div>
										)
									})}
								</Modal.Body>
								<Modal.Footer className='container-button-modal'>
									<Form.Group className='form-group-previous'>
										<Button variant='secondary' className='btn-close-modal' onClick={handleClose}>
											Fechar
										</Button>
									</Form.Group>
									<Form.Group className='form-group-next'>
										<Button className='btn-save-modal' variant='primary' type='submit' onClick={execute}>
											Salvar
										</Button>
									</Form.Group>
								</Modal.Footer>
							</Modal>
							<Modal
								className='modal'
								show={showRole}
								onHide={handleClose}
								size='lg'
								aria-labelledby='contained-modal-title-vcenter'
								centered
							>
								<Modal.Header>
									<Modal.Title className='modal-title'>Instrumentos</Modal.Title>
								</Modal.Header>
								<Modal.Body className='modal-body'>
									{rolesNotSelected.map((role) => {
										return (
											<div
												className='option-genre'
												onClick={() => {
													addFromListRole(role)
													setRolesNotSelected(rolesNotSelected.filter((roleNotSelected) => roleNotSelected !== role))
												}}
											>
												{RoleNameEnumDesc.get(role)} <i className='bx bx-x'></i>
											</div>
										)
									})}
								</Modal.Body>
								<hr className='hr' />
								<Modal.Body className='modal-body'>
									{listRole.roles.map((role) => {
										return (
											<div className='option-genre-select' onClick={() => {
                                                    removeFromListRole(role)
                                                    setRolesNotSelected([...rolesNotSelected, role])
                                                }}>
												{RoleNameEnumDesc.get(role)} <i className='bx bx-check'></i>
											</div>
										)
									})}
								</Modal.Body>
								<Modal.Footer>
									<Form.Group className='form-group-previous'>
										<Button variant='secondary' className='btn-close-modal' onClick={handleClose}>
											Fechar
										</Button>
									</Form.Group>
									<Form.Group className='form-group-next'>
										<Button className='btn-save-modal' variant='primary' type='submit' onClick={execute}>
											Salvar
										</Button>
									</Form.Group>
								</Modal.Footer>
							</Modal>
							<Form.Group controlId='formGroupSkillLevel' className='form-group-all'>
								<Form.Select
									value={formState.skillLevel}
									onChange={(event) => handleChange(event.target.value, "skillLevel")}
									className='form-select'
								>
									<option value={SkillLevelEnum.NULL}>Selecione o seu Nível</option>
									<option value={SkillLevelEnum.BEGINNER}>{SkillLevelEnumDesc.get(SkillLevelEnum.BEGINNER)}</option>
									<option value={SkillLevelEnum.INTERMEDIATE}>
										{SkillLevelEnumDesc.get(SkillLevelEnum.INTERMEDIATE)}
									</option>
									<option value={SkillLevelEnum.ADVANCED}>{SkillLevelEnumDesc.get(SkillLevelEnum.ADVANCED)}</option>
									<option value={SkillLevelEnum.EXPERT}>{SkillLevelEnumDesc.get(SkillLevelEnum.EXPERT)}</option>
								</Form.Select>
							</Form.Group>
							<Form.Group controlId='formGroupBirthDate' className='form-group-all'>
								<Form.Control
									value={formState.birthDate}
									onChange={(event) => handleChange(event.target.value, "birthDate")}
									type='date'
									placeholder='Data de nascimento'
									className='input-all form-label'
								/>
							</Form.Group>
							<Form.Group controlId='formGroupDescription' className='form-group-all'>
								<Form.Control
									value={formState.description}
									onChange={(event) => handleChange(event.target.value, "description")}
									type='text'
									placeholder='Descrição'
									className='input-all form-label'
								/>
							</Form.Group>
						</div>
						<Form.Group className='form-group-previous'>
							<Button onClick={previousStep} type='submit' className='button'>
								Voltar
							</Button>
						</Form.Group>
						<Form.Group className='form-group-next'>
							<Button type='submit' className='button'>
								Finalizar
							</Button>
						</Form.Group>
					</Form>
				</div>
				<div className='register-container-right'>
					<img src={LogoHorizontal} alt='' />
					<h1>
						Crie sua conta
						<br />
						aqui agora!
					</h1>
					<p>Tocar uma nota equivocada é insignificante. Tocar sem paixão é imperdoável</p>
				</div>
			</div>
		</>
	)
}
