import { useState } from "react"
import { Button, Form, Modal } from "react-bootstrap"
import { useForm, SubmitHandler } from "react-hook-form"
import { Box } from "@mui/material"
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers"
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns"
import { ptBR } from "date-fns/locale"
import ArrowDropDown from "@mui/icons-material/ArrowDropDown"

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

const InputChips = ({
	open = false,
	title = "",
	items = [],
	handleInternationalization,
	handleSelect,
	handleDeselect,
	handleConfirm,
	handleClose,
}: any) => {
	const [itemsNotSelected, setItemsNotSelected] = useState<any[]>(items)
	const [itemsSelected, setItemsSelected] = useState<any[]>([])

	const handleSwitch = (item: any, selected: boolean) => {
		if (selected) {
			setItemsSelected([...itemsSelected, item])
			setItemsNotSelected(itemsNotSelected.filter((itemNotSelected) => itemNotSelected !== item))
		} else {
			setItemsSelected(itemsSelected.filter((itemNotSelected) => itemNotSelected !== item))
			setItemsNotSelected([...itemsNotSelected, item])
		}
	}

	return (
		<Modal
			className='modal'
			centered
			show={open}
			size='lg'
			onHide={handleClose}
			aria-labelledby='contained-modal-title-vcenter'
		>
			<Modal.Header>
				<Modal.Title className='modal-title'>{title}</Modal.Title>
			</Modal.Header>
			<Modal.Body className='modal-body'>
				{itemsNotSelected.map((item, index) => (
					<div
						key={`not-selected-${item}-${index}`}
						className='action-chip'
						onClick={() => {
							handleSelect(item)
							handleSwitch(item, true)
						}}
					>
						{handleInternationalization(item)} <i className='bx bx-x'></i>
					</div>
				))}
			</Modal.Body>
			<hr className='hr' />
			<Modal.Body className='modal-body'>
				{itemsSelected.map((item, index) => {
					return (
						<div
							key={`selected-${item}-${index}`}
							className='action-chip-select'
							onClick={() => {
								handleDeselect(item)
								handleSwitch(item, false)
							}}
						>
							{handleInternationalization(item)} <i className='bx bx-check'></i>
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
					<Button className='btn-save-modal' variant='primary' type='submit' onClick={handleConfirm}>
						Salvar
					</Button>
				</Form.Group>
			</Modal.Footer>
		</Modal>
	)
}

const defaultGenresNotSelected = [
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

const defaultRolesNotSelected = [
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
	const [genresNotSelected, setGenresNotSelected] = useState(defaultGenresNotSelected as GenreNameEnum[])
	const [rolesNotSelected, setRolesNotSelected] = useState(defaultRolesNotSelected as RoleNameEnum[])

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
		handleClose()
	}

	return (
		<>
			<div className='max-width-height'>
				<div className='register-container-left'>
					<Form onSubmit={handleSubmit(onSubmit)}>
						<div className='register-form'>
							<h2>Fale um pouco mais sobre você</h2>
							<button className='form-group-modal' type='button' onClick={() => handleShow("genre")}>
								<span>
									{listGenre.genres.length > 0 ? (
										listGenre.genres.map((item) => (
											<div className='action-chip-show'>
												{item} <i className='bx bx-check'></i>
											</div>
										))
									) : (
										<p>Adicionar gênero musical</p>
									)}
								</span>
								<i className='bx bx-x'></i>
							</button>
							<button className='form-group-modal' type='button' onClick={() => handleShow("role")}>
								<span>
									{listRole.roles.length > 0 ? (
										listRole.roles.map((item) => (
											<div className='action-chip-show'>
												{item} <i className='bx bx-check'></i>
											</div>
										))
									) : (
										<p>Adicionar Instrumento</p>
									)}
								</span>
								<i className='bx bx-x'></i>
							</button>
							<InputChips
								open={showGenre}
								title='Gênero Musical'
								items={genresNotSelected}
								handleInternationalization={(genre: any) => GenreNameEnumDesc.get(genre)}
								handleSelect={(genre: any) => addFromListGenre(genre)}
								handleDeselect={(genre: any) => removeFromListGenre(genre)}
								handleConfirm={() => execute()}
								handleClose={() => handleClose()}
							/>
							<InputChips
								open={showRole}
								title='Instrumentos'
								items={rolesNotSelected}
								handleInternationalization={(role: any) => RoleNameEnumDesc.get(role)}
								handleSelect={(role: any) => addFromListRole(role)}
								handleDeselect={(role: any) => removeFromListRole(role)}
								handleConfirm={() => execute()}
								handleClose={() => handleClose()}
							/>
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
								<ArrowDropDown />
							</Form.Group>
							<Form.Group controlId='formGroupBirthDate' className='form-group-all'>
								<LocalizationProvider adapterLocale={ptBR} dateAdapter={AdapterDateFns}>
									<DatePicker
										value={formState.birthDate}
										onChange={(dateTime: any) => handleChange(dateTime, "birthDate")}
										renderInput={({ inputRef, inputProps, InputProps }) => (
											<Box
												className='form-label'
												sx={{ display: "flex", alignItems: "center", justifyContent: "space-between" }}
											>
												<Form.Control
													{...inputProps}
													disabled
													ref={inputRef}
													placeholder='Data de nascimento'
													className='form-label-date'
												/>
												{InputProps?.endAdornment}
											</Box>
										)}
									/>
								</LocalizationProvider>
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
