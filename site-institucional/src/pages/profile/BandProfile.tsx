import React, { useEffect, useMemo, useState } from "react"
import { ArrowDropDown, InsertChart, MusicNote, Piano } from "@mui/icons-material"
import { ClickAwayListener } from "@mui/material"
import { NavBar } from "../../components/Navbar"
import ProfilePost from "./components/ProfilePost/ProfilePost"
import ProfileHighlight from "./components/ProfileHighlight/ProfileHighlight"
import ProfileIntro, { PROFILE_TYPE } from "./components/ProfileIntro/ProfileIntro"
import ProfileNavigatorTabs from "./components/ProfileNavigationTabs/ProfileNavigationTabs"
import "./profile.style.css"

// TODO REMOVER ESSE MOCK
const HIGHLIGHT_MOCK = [
	{
		bannerSrc: "https://lastfm.freetls.fastly.net/i/u/ar0/bd84742a1f46cd1e9050d71520159ce0",
		avatarSrc: "https://lastfm.freetls.fastly.net/i/u/ar0/38fbff196c0860ec51fc7be11ad7a19e.jpg",
		userInfo: { name: "Burnout Syndrome", description: "PUTA BANDA! 🎸WUÉÉEEEUNN 🎤 AAAAA!" },
	},
	{
		bannerSrc:
			"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR4CJ-_M93XzMWK2K27WvbxPWvNgA9PGtkr1suJhyrg7dvibiHSE9SHdBq4plRT1UMKcbs&usqp=CAU",
		avatarSrc: "https://i.pinimg.com/736x/82/92/b8/8292b855a223d714cf1975e50f3b0ed5.jpg",
		userInfo: { name: "Reol", description: "Ainda é uma Banda de certa forma ¯\\_(ツ)_/¯" },
	},
]

const SHOWCASE_INTRO_MOCK = [
	{
		imageSrc: "https://i.pinimg.com/736x/82/92/b8/8292b855a223d714cf1975e50f3b0ed5.jpg",
		name: "Reol",
		role: "Vocalista",
		leader: true,
	},
	{
		imageSrc: "https://lastfm.freetls.fastly.net/i/u/ar0/e63de329c7db8ddccd1a1b0ee9ba5d67.jpg",
		name: "Giga",
		role: "Produtor",
		leader: false,
	},
	{
		imageSrc: "https://pm1.narvii.com/6314/ea5666b0d0583fef6c5ed8a06207e0b9bf1dfb7a_hq.jpg",
		name: "Okiku",
		role: "Designer",
		leader: false,
	}
]

const Popover = ({ open = false, children = <span></span> }: { open: boolean; children: any }) => {
	return <div className={`popover-menu ${open ? "popover-menu--active" : ""}`}>{children}</div>
}

const BandProfile = () => {
	const [counter, setCounter] = useState(0)

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
												<a>Integrantes da Banda</a>
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
					<ProfileIntro infos={SHOWCASE_INTRO_MOCK} type={PROFILE_TYPE.BAND} />
					<ProfilePost />
				</div>
			</div>
		</>
	)
}
export default BandProfile
