import React, { useEffect, useMemo, useState } from "react"
import {
	Dialog,
	DialogTitle,
	DialogContent,
	DialogActions,
	Box,
	Button,
	CircularProgress,
	ClickAwayListener,
} from "@mui/material"
import { ArrowDropDown, InsertChart, MusicNote, Piano } from "@mui/icons-material"
import { ptBR } from "date-fns/locale"
import { NavBar } from "../../components/Navbar"
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns"
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers"
import ProfilePost from "./components/ProfilePost/ProfilePost"
import ProfileHighlight from "./components/ProfileHighlight/ProfileHighlight"
import ProfileIntro, { PROFILE_TYPE } from "./components/ProfileIntro/ProfileIntro"
import ProfileNavigatorTabs from "./components/ProfileNavigationTabs/ProfileNavigationTabs"
import "./profile.style.css"

// TODO REMOVER ESSE MOCK
const HIGHLIGHT_MOCK = [
	{
		bannerSrc: "https://www.moshimoshi-nippon.jp/wp/wp-content/uploads/2019/12/LiSA_YouTube.jpg",
		avatarSrc: "http://pm1.narvii.com/6842/9ec207fa5fd073a64397a15dd3e7ed668066adfcv2_00.jpg",
		userInfo: { name: "Lisa", description: "Canta demais essa também nota 11/10" },
	},
	{
		bannerSrc:
			"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR4CJ-_M93XzMWK2K27WvbxPWvNgA9PGtkr1suJhyrg7dvibiHSE9SHdBq4plRT1UMKcbs&usqp=CAU",
		avatarSrc: "https://i.pinimg.com/736x/82/92/b8/8292b855a223d714cf1975e50f3b0ed5.jpg",
		userInfo: { name: "Reol", description: "RAINHA DEUSA PERFEITA NUNCA ERROU MELHOR DE TODAS" },
	},
	{
		bannerSrc:
			"https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/4d87b0f4-ae26-4b18-835c-af3a7ce1349f/ddd9v97-cf486455-2052-43b0-9687-6fa4a68d425f.png/v1/fill/w_1024,h_217,q_80,strp/shrek_banner_by_happaxgamma_ddd9v97-fullview.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9MjE3IiwicGF0aCI6IlwvZlwvNGQ4N2IwZjQtYWUyNi00YjE4LTgzNWMtYWYzYTdjZTEzNDlmXC9kZGQ5djk3LWNmNDg2NDU1LTIwNTItNDNiMC05Njg3LTZmYTRhNjhkNDI1Zi5wbmciLCJ3aWR0aCI6Ijw9MTAyNCJ9XV0sImF1ZCI6WyJ1cm46c2VydmljZTppbWFnZS5vcGVyYXRpb25zIl19.FWOX_P0XyiINOOHitIcSpbUIGtMe7rTo9aiFzCU7_D8",
		avatarSrc:
			"https://a-static.mlcdn.com.br/280x210/painel-de-festa-shrek-03-colormyhome/colormyhome/5423/3f36b8b79479674467cf7ac403310d42.jpg",
		userInfo: { name: "Shrek", description: "Achou que ele não cantava ? erro" },
	},
]

const SHOWCASE_INTRO_MOCK = [
	{
		icon: InsertChart,
		label: "Expert",
	},
	{
		icon: MusicNote,
		label: "Cantor",
	},
	{
		icon: Piano,
		label: "Pianista",
	}
]

const Popover = ({ open = false, children = <span></span> }: { open: boolean; children: any }) => {
	return <div className={`popover-menu ${open ? "popover-menu--active" : ""}`}>{children}</div>
}

const UserProfile = () => {
	const [counter, setCounter] = useState(0)
	const [loadingRegisterBandConfirm, setLoadingRegisterBandConfirm] = useState(false)
	const [registerBandForm, setRegisterBandForm] = useState({ name: "", date: "", description: "", genre: "" })
	const [registerBandDialogVisibility, setRegisterBandDialogVisibility] = useState(false)

	// TODO REMOVER ESSA BRINCADEIRA AQUI
	const a = () => {
		setTimeout(() => {
			if (counter === HIGHLIGHT_MOCK.length - 1) {
				setCounter(0)
			} else {
				setCounter(counter + 1)
			}
		}, 3000)
	}

	useEffect(() => {
		a()
	}, [counter])
	// até aqui -------------------------

	const handleRegisterBand = () => {
		try {
			setLoadingRegisterBandConfirm(true)
			// Request enviando os dados pro Back-end
		} catch {
			// Tratar qualquer erro na request aqui
		} finally {
			setLoadingRegisterBandConfirm(false)
		}
	}

	const PROFILE_NAVIGATION_OPTIONS = useMemo(
		() => [
			{
				label: "Feed",
				handleClick: () => {},
			},
			{
				label: "Sobre",
				handleClick: () => {},
			},
			{
				label: "Videos",
				handleClick: () => {},
			},
			{
				label: "Playlist",
				handleClick: () => {},
			},
			{
				label: "Mais",
				handleClick: () => {},
				CustomOption: ({ currentTab, handleChangeTab }: { currentTab: number; handleChangeTab: Function }) => {
					const [menuVisibility, setMenuVisibility] = useState(false)
					return (
						<div>
							<ClickAwayListener onClickAway={() => setMenuVisibility(false)}>
								<div>
									<a
										onClick={() => {
											handleChangeTab(currentTab)
											setMenuVisibility(true)
										}}
									>
										Mais
										<ArrowDropDown />
									</a>
									<Popover open={menuVisibility}>
										<ul className='popover-options'>
											<li>
												<a>Chat</a>
											</li>
											<li>
												<a onClick={() => setRegisterBandDialogVisibility(true)}>Criar uma banda</a>
											</li>
										</ul>
									</Popover>
								</div>
							</ClickAwayListener>
						</div>
					)
				},
			},
		],
		[]
	)

	return (
		<>
			<Dialog
				className='registerBandDialog'
				open={registerBandDialogVisibility}
				onClose={() => setRegisterBandDialogVisibility(false)}
			>
				<DialogTitle>Vamos preencher os dados da sua banda ?</DialogTitle>
				<DialogContent className='registerBandDialogContent'>
					<input
						className='form-label'
						type='text'
						placeholder='Nome'
						value={registerBandForm.name}
						onChange={(event) => setRegisterBandForm({ ...registerBandForm, name: event.target.value })}
					/>
					<LocalizationProvider adapterLocale={ptBR} dateAdapter={AdapterDateFns}>
						<DatePicker
							value={registerBandForm.date}
							onChange={(dateTime: any) => setRegisterBandForm({ ...registerBandForm, date: dateTime })}
							renderInput={({ inputRef, inputProps, InputProps }) => (
								<Box
									className='form-label'
									sx={{ display: "flex", alignItems: "center", justifyContent: "space-between" }}
								>
									<input
										{...inputProps}
										disabled
										type='text'
										ref={inputRef}
										placeholder='Data de Criação da Banda'
										className='form-label-date'
									/>
									{InputProps?.endAdornment}
								</Box>
							)}
						/>
					</LocalizationProvider>
					<textarea
						className='form-label'
						placeholder='Descreva sobre usa Banda!'
						value={registerBandForm.description}
						onChange={(event) => setRegisterBandForm({ ...registerBandForm, description: event.target.value })}
					/>
					<input
						className='form-label'
						type='text'
						placeholder='Vamos categorizar o gênero da Banda?'
						value={registerBandForm.genre}
						onChange={(event) => setRegisterBandForm({ ...registerBandForm, genre: event.target.value })}
					/>
					<DialogActions sx={{ display: "flex", justifyContent: "space-between", padding: "8px 0" }}>
						<Button
							variant='outlined'
							color='error'
							onClick={() => {
								setRegisterBandDialogVisibility(false)
								setRegisterBandForm({ name: "", date: "", description: "", genre: "" })
							}}
						>
							Cancelar
						</Button>
						<Button
							variant='contained'
							sx={{
								width: 100,
								backgroundColor: "var(--dark-purple)",
								"&:hover": { backgroundColor: "var(--light-purple)" },
							}}
							onClick={handleRegisterBand}
						>
							{loadingRegisterBandConfirm ? <CircularProgress size={24} /> : "Próximo"}
						</Button>
					</DialogActions>
				</DialogContent>
			</Dialog>

			<NavBar isbtnRegisterOff />
			<div className='profilePageWrapper'>
				<div className='profileTopic'>
					<ProfileHighlight
						bannerSrc={HIGHLIGHT_MOCK[counter].bannerSrc}
						avatarSrc={HIGHLIGHT_MOCK[counter].avatarSrc}
						userInfo={HIGHLIGHT_MOCK[counter].userInfo}
					/>
					<ProfileNavigatorTabs options={PROFILE_NAVIGATION_OPTIONS} />
				</div>
				<div className='profileShowcaseWrapper'>
					<ProfileIntro infos={SHOWCASE_INTRO_MOCK} type={PROFILE_TYPE.USER} />
					<ProfilePost />
				</div>
			</div>
		</>
	)
}

export default UserProfile
