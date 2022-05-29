import { Button, Form } from "react-bootstrap"
import { useState } from "react"
import { useNavigate, Link } from "react-router-dom"
import ArrowDropDown from "@mui/icons-material/ArrowDropDown"

import "../../styles/register.css"

import LogoHorizontal from "../../assets/img/logo-fundo-escuro-texto-horizontal.png"

import { IFormUserState } from "../../components/MultiForm"

import { StateEnum } from "../../enums/StateEnum"
import { SexEnum, SexEnumDesc } from "../../enums/SexEnum"

interface Iprops {
	nextStep: () => void
	formState: IFormUserState
	handleChange: (value: any, fieldName: string) => void
}

export function Register(props: Iprops) {
	const navigate = useNavigate()

	const { nextStep, formState, handleChange } = props

	const cancelRegister = () => {
		navigate("/")
	}

	return (
		<>
			<div className='max-width-height'>
				<div className='register-container-left'>
					<Form>
						<div className='register-form'>
							<h2>Crie sua conta</h2>
							<Form.Group controlId='formGroupNome'>
								<Form.Control
									value={formState.name}
									onChange={(event) => handleChange(event.target.value, "name")}
									type='text'
									placeholder='Nome'
									className='input-all form-label'
								/>
							</Form.Group>
							<div className='container-input-hlf'>
								<Form.Group controlId='formGroupEmail' className='form-group'>
									<Form.Control
										value={formState.email}
										onChange={(event) => handleChange(event.target.value, "email")}
										type='text'
										placeholder='E-mail'
										className='input-all form-label'
									/>
								</Form.Group>
								<Form.Group controlId='formGroupCpf' className='form-group'>
									<Form.Select
										value={formState.sex}
										onChange={(event) => handleChange(event.target.value, "sex")}
										className='form-select'
									>
										<option value={SexEnum.NULL} selected>
											Selecione o seu Sexo
										</option>
										<option value={SexEnum.MALE}>{SexEnumDesc.get(SexEnum.MALE)}</option>
										<option value={SexEnum.FEMALE}>{SexEnumDesc.get(SexEnum.FEMALE)}</option>
										<option value={SexEnum.NOT_APPLICABLE}>{SexEnumDesc.get(SexEnum.NOT_APPLICABLE)}</option>
										<option value={SexEnum.NOT_KNOWN}>{SexEnumDesc.get(SexEnum.NOT_KNOWN)}</option>
									</Form.Select>
									<ArrowDropDown />
								</Form.Group>
							</div>
							<div className='container-input-hlf'>
								<Form.Group controlId='formGroupEstado' className='form-group'>
									<Form.Select
										value={formState.state}
										onChange={(event) => handleChange(event.target.value, "state")}
										className='form-select'
									>
										<option value={StateEnum.NULL}>Selecione o seu Estado</option>
										<option value={StateEnum.AC}>{StateEnum.AC}</option>
										<option value={StateEnum.AL}>{StateEnum.AL}</option>
										<option value={StateEnum.AP}>{StateEnum.AP}</option>
										<option value={StateEnum.AM}>{StateEnum.AM}</option>
										<option value={StateEnum.BA}>{StateEnum.BA}</option>
										<option value={StateEnum.CE}>{StateEnum.CE}</option>
										<option value={StateEnum.DF}>{StateEnum.DF}</option>
										<option value={StateEnum.ES}>{StateEnum.ES}</option>
										<option value={StateEnum.GO}>{StateEnum.GO}</option>
										<option value={StateEnum.MA}>{StateEnum.MA}</option>
										<option value={StateEnum.MT}>{StateEnum.MT}</option>
										<option value={StateEnum.MS}>{StateEnum.MS}</option>
										<option value={StateEnum.MG}>{StateEnum.MG}</option>
										<option value={StateEnum.PA}>{StateEnum.PA}</option>
										<option value={StateEnum.PB}>{StateEnum.PB}</option>
										<option value={StateEnum.PR}>{StateEnum.PR}</option>
										<option value={StateEnum.PE}>{StateEnum.PE}</option>
										<option value={StateEnum.PI}>{StateEnum.PI}</option>
										<option value={StateEnum.RJ}>{StateEnum.RJ}</option>
										<option value={StateEnum.RN}>{StateEnum.RN}</option>
										<option value={StateEnum.RS}>{StateEnum.RS}</option>
										<option value={StateEnum.RO}>{StateEnum.RO}</option>
										<option value={StateEnum.RR}>{StateEnum.RR}</option>
										<option value={StateEnum.SC}>{StateEnum.SC}</option>
										<option value={StateEnum.SP}>{StateEnum.SP}</option>
										<option value={StateEnum.SE}>{StateEnum.SE}</option>
										<option value={StateEnum.TO}>{StateEnum.TO}</option>
									</Form.Select>
									<ArrowDropDown />
								</Form.Group>
								<Form.Group controlId='formGroupCidade' className='form-group'>
									<Form.Control
										value={formState.city}
										onChange={(event) => handleChange(event.target.value, "city")}
										type='text'
										placeholder='Cidade'
										className='input-all form-label'
									/>
								</Form.Group>
							</div>
							<Form.Group controlId='formGroupSenha'>
								<Form.Control
									value={formState.password}
									onChange={(event) => handleChange(event.target.value, "password")}
									type='password'
									placeholder='Senha'
									className='input-all form-label'
								/>
							</Form.Group>
							<Form.Group controlId='formGroupConfirmarSenha'>
								<Form.Control
									value={formState.confirmPassword}
									onChange={(event) => handleChange(event.target.value, "confirmPassword")}
									type='password'
									placeholder='Confirmar Senha'
									className='input-all form-label'
								/>
							</Form.Group>
						</div>
						<Form.Group className='form-group-previous'>
							<Button onClick={cancelRegister} className='button'>
								Cancelar
							</Button>
						</Form.Group>
						<Form.Group className='form-group-next'>
							<Button onClick={nextStep} type='submit' className='button'>
								Próximo
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
					<p>Tocar uma nota equivocada é insignificante.Tocar sem paixão é imperdoável</p>
				</div>
			</div>
		</>
	)
}
