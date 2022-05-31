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
import {
	ArrowDropDown,
	InsertChart,
	MusicNote,
	Piano,
	SvgIconComponent as ISvgIconComponent,
} from "@mui/icons-material"
import { ptBR } from "date-fns/locale"
import { useLocation, useNavigate } from "react-router-dom"
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns"
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers"
import { NavBar } from "../../components/Navbar"
import ProfilePost from "./components/ProfilePost/ProfilePost"
import ProfileHighlight from "./components/ProfileHighlight/ProfileHighlight"
import ProfileIntro, { PROFILE_TYPE } from "./components/ProfileIntro/ProfileIntro"
import ProfileNavigatorTabs from "./components/ProfileNavigationTabs/ProfileNavigationTabs"
import "./profile.style.css"

interface IUserPageData {
	profileUserId: number
	profileHighlight: {
		userInfo: { name: string; description: string }
		avatarSrc: string
		bannerSrc: string
	}
	profileIntroSkills: {
		icon: ISvgIconComponent
		label: string
	}[]
	profileIntroBands: {
		bandId: number
		name: string
		leader: boolean
		role: string
		imageSrc?: string
	}[]
}
// Dados mockados da Página
const REOL_USER_PROFILE_PAGE_MOCK: IUserPageData = {
	profileUserId: 1,
	profileHighlight: {
		userInfo: { name: "Reol", description: "RAINHA DEUSA PERFEITA NUNCA ERROU MELHOR DE TODAS" },
		avatarSrc: "https://i.pinimg.com/736x/82/92/b8/8292b855a223d714cf1975e50f3b0ed5.jpg",
		bannerSrc:
			"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR4CJ-_M93XzMWK2K27WvbxPWvNgA9PGtkr1suJhyrg7dvibiHSE9SHdBq4plRT1UMKcbs&usqp=CAU",
	},
	profileIntroSkills: [
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
		},
	],
	profileIntroBands: [
		{
			bandId: 1,
			name: "Reol",
			leader: true,
			role: "Vocalista",
			imageSrc: "https://i.pinimg.com/736x/82/92/b8/8292b855a223d714cf1975e50f3b0ed5.jpg",
		},
	],
}

const SUMETAL_USER_PROFILE_PAGE_MOCK: IUserPageData = {
	profileUserId: 2,
	profileHighlight: {
		userInfo: { name: "Suzuka Nakamoto", description: "😗🤘 Megitsune-🦊" },
		avatarSrc:
			"https://images.moviefit.me/p/o/135051-suzuka-nakamoto.jpg",
		bannerSrc: "https://media.gettyimages.com/photos/sumetal-of-babymetal-performs-on-day-3-of-the-leeds-festival-at-park-picture-id486026350?s=612x612",
	},
	profileIntroSkills: [
		{
			icon: MusicNote,
			label: "Cantora",
		},
	],
	profileIntroBands: [
		{
			bandId: 2,
			name: "BABYMETAL",
			leader: true,
			role: "Vocalista",
			imageSrc:
				"https://studiosol-a.akamaihd.net/uploadfile/letras/albuns/9/4/0/2/381521408793193.jpg",
		},
	],
}

const MOAMETAL_USER_PROFILE_PAGE_MOCK: IUserPageData = {
	profileUserId: 3,
	profileHighlight: {
		userInfo: { name: "Moa Kikuchi", description: "🤘 Cadê minhas irmã gêmea mãe ?" },
		avatarSrc:
			"https://i.pinimg.com/originals/ac/60/c4/ac60c4a8383950f04fefaf3d0900192c.jpg",
		bannerSrc: "https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/Babymetal_-_2018152162203_2018-06-01_Rock_am_Ring_-_1D_X_MK_II_-_0347_-_B70I0418.jpg/1200px-Babymetal_-_2018152162203_2018-06-01_Rock_am_Ring_-_1D_X_MK_II_-_0347_-_B70I0418.jpg",
	},
	profileIntroSkills: [
		{
			icon: MusicNote,
			label: "Cantora",
		},
	],
	profileIntroBands: [
		{
			bandId: 2,
			name: "BABYMETAL",
			leader: false,
			role: "Vocal",
			imageSrc:
				"https://studiosol-a.akamaihd.net/uploadfile/letras/albuns/9/4/0/2/381521408793193.jpg",
		},
	],
}

const YUIMETAL_USER_PROFILE_PAGE_MOCK: IUserPageData = {
	profileUserId: 4,
	profileHighlight: {
		userInfo: { name: "Yui Mizuno", description: "Saiu da banda 😭" },
		avatarSrc:
			"https://4.bp.blogspot.com/-Bel3Lb_a1H8/W7fgAzWXPwI/AAAAAAAABD4/2QFXrSiDbmIndQv8mYAhd_uON5LeG5C_ACEwYBhgL/s1600/DoA81Y-XcAAiPa5.jpg",
		bannerSrc: "https://pm1.narvii.com/6348/c5994923f8975f312f51769f78779a6c0072dfb7_hq.jpg",
	},
	profileIntroSkills: [
		{
			icon: MusicNote,
			label: "Ex-Cantora",
		},
	],
	profileIntroBands: [
		{
			bandId: 2,
			name: "BABYMETAL",
			leader: false,
			role: "Vocal",
			imageSrc:
				"https://studiosol-a.akamaihd.net/uploadfile/letras/albuns/9/4/0/2/381521408793193.jpg",
		},
	],
}

const MOCK_GET_PROFILE_USER_DATA = (userId: number) => {
	const MOCK_DATABASE_USERS = [
		{
			id: 1,
			data: REOL_USER_PROFILE_PAGE_MOCK,
		},
		{
			id: 2,
			data: SUMETAL_USER_PROFILE_PAGE_MOCK,
		},
		{
			id: 3,
			data: MOAMETAL_USER_PROFILE_PAGE_MOCK,
		},
		{
			id: 4,
			data: YUIMETAL_USER_PROFILE_PAGE_MOCK,
		},
	]

	return MOCK_DATABASE_USERS.find((user) => user.id === userId)?.data || undefined
}
// MOCK ------------------

const Popover = ({ open = false, children = <span></span> }: { open: boolean; children: any }) => {
	return <div className={`popover-menu ${open ? "popover-menu--active" : ""}`}>{children}</div>
}

const UserProfile = () => {
	const [currentShowcasePage, setCurrentShowcasePage] = useState(0)
	const [userProfileData, setUserProfileData] = useState<IUserPageData | any>(undefined)
	const [registerBandDialogVisibility, setRegisterBandDialogVisibility] = useState(false)
	const [registerBandForm, setRegisterBandForm] = useState({ name: "", date: "", description: "", genre: "" })
	
	const [loadingProfielUserData, setLoadingProfielUserData] = useState(false)
	const [loadingRegisterBandConfirm, setLoadingRegisterBandConfirm] = useState(false)
	const location = useLocation()
	const navigate = useNavigate()
	
	const getProfileUserData = async (userId: number) => {
		try {
			setLoadingProfielUserData(true)
			// Trocar pela request API puxando os dados do Perfil do Usuário
			const response = MOCK_GET_PROFILE_USER_DATA(userId)
			if (response === undefined) navigate("/")
			setUserProfileData(response)
		} catch {
			// Validar erros da request
		} finally {
			// Remover Timeout
			setTimeout(() => {
				setLoadingProfielUserData(false)
			}, 500)
		}
	}

	const registerNewBand = async () => {
		try {
			setLoadingRegisterBandConfirm(true)
			// Trocar pela request API puxando os dados do Perfil da Banda
			// const response = REOL_USER_PROFILE_PAGE_MOCK
			// setUserProfileData(response)
		} catch {
			// Validar erros da request
		} finally {
			// Remover Timeout
			setTimeout(() => {
				setLoadingRegisterBandConfirm(false)
			}, 500)
		}
	}

	useEffect(() => {
		const profilePageId: string[] = location.pathname.match(/\d+$/) || ["-1"]
		getProfileUserData(Number.parseInt(profilePageId[0]))
	}, [])

	const PROFILE_NAVIGATION_OPTIONS = useMemo(
		() => [
			{
				label: "Feed",
				handleClick: (tab: number) => {
					setCurrentShowcasePage(tab)
				},
			},
			{
				label: "Sobre",
				handleClick: (tab: number) => {
					setCurrentShowcasePage(tab)
				},
			},
			{
				label: "Videos",
				handleClick: (tab: number) => {
					setCurrentShowcasePage(tab)
				},
			},
			{
				label: "Playlist",
				handleClick: (tab: number) => {
					setCurrentShowcasePage(tab)
				},
			},
			{
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

	const PROFILE_SHOWCASE_PAGES = [
		{
			page: 0,
			renderPage: () => (
				<>
					<ProfileIntro
						infos={{
							skills: userProfileData?.profileIntroSkills,
							bands: userProfileData?.profileIntroBands,
						}}
						type={PROFILE_TYPE.USER}
						handleClick={(id: number) => navigate('/band/'+id)}
					/>
					<ProfilePost />
				</>
			),
		},
	]

	return (
		<>
			<Dialog
				fullWidth
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
							onClick={() => registerNewBand()}
						>
							{loadingRegisterBandConfirm ? <CircularProgress size={24} /> : "Próximo"}
						</Button>
					</DialogActions>
				</DialogContent>
			</Dialog>

			<NavBar isbtnRegisterOff />
			<div className='profilePageWrapper'>
				{loadingProfielUserData ? (
					<CircularProgress />
				) : (
					<>
						<div className='profileTopic'>
							<ProfileHighlight
								bannerSrc={userProfileData?.profileHighlight.bannerSrc}
								avatarSrc={userProfileData?.profileHighlight.avatarSrc}
								userInfo={userProfileData?.profileHighlight.userInfo}
							/>
							<ProfileNavigatorTabs options={PROFILE_NAVIGATION_OPTIONS} />
						</div>
						<div className='profileShowcaseWrapper'>
							{PROFILE_SHOWCASE_PAGES.find((showcase) => showcase.page === currentShowcasePage)?.renderPage()}
						</div>
					</>
				)}
			</div>
		</>
	)
}

export default UserProfile
