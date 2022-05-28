import React, { useEffect, useState } from "react"
import { NavBar } from "../../components/Navbar"
import ProfileHighlight from "./components/ProfileHighlight"
import ProfileNavigatorTabs from "./components/ProfileNavigationTabs"
import ProfilePost from "./components/ProfilePost"
import "./profile.style.css"

// TODO REMOVER ESSE MOCK
const HIGHLIGHT_MOCK = [
	{
		bannerSrc: "https://www.moshimoshi-nippon.jp/wp/wp-content/uploads/2019/12/LiSA_YouTube.jpg",
		avatarSrc: "http://pm1.narvii.com/6842/9ec207fa5fd073a64397a15dd3e7ed668066adfcv2_00.jpg",
		userInfo: { name: "Lisa", description: "Canta demais essa também nota 11/10" },
	},
	{
		bannerSrc: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR4CJ-_M93XzMWK2K27WvbxPWvNgA9PGtkr1suJhyrg7dvibiHSE9SHdBq4plRT1UMKcbs&usqp=CAU",
		avatarSrc: "https://i.pinimg.com/736x/82/92/b8/8292b855a223d714cf1975e50f3b0ed5.jpg",
		userInfo: { name: "Reol", description: "RAINHA DEUSA PERFEITA NUNCA ERROU MELHOR DE TODAS" },
	},
	{
		bannerSrc: "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/4d87b0f4-ae26-4b18-835c-af3a7ce1349f/ddd9v97-cf486455-2052-43b0-9687-6fa4a68d425f.png/v1/fill/w_1024,h_217,q_80,strp/shrek_banner_by_happaxgamma_ddd9v97-fullview.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9MjE3IiwicGF0aCI6IlwvZlwvNGQ4N2IwZjQtYWUyNi00YjE4LTgzNWMtYWYzYTdjZTEzNDlmXC9kZGQ5djk3LWNmNDg2NDU1LTIwNTItNDNiMC05Njg3LTZmYTRhNjhkNDI1Zi5wbmciLCJ3aWR0aCI6Ijw9MTAyNCJ9XV0sImF1ZCI6WyJ1cm46c2VydmljZTppbWFnZS5vcGVyYXRpb25zIl19.FWOX_P0XyiINOOHitIcSpbUIGtMe7rTo9aiFzCU7_D8",
		avatarSrc: "https://a-static.mlcdn.com.br/280x210/painel-de-festa-shrek-03-colormyhome/colormyhome/5423/3f36b8b79479674467cf7ac403310d42.jpg",
		userInfo: { name: "Shrek", description: "Achou que ele não cantava ? erro" },
	},
]

const SHOWCASE_INTRO_MOCK = []

const Profile = () => {
	const [currentTab, setCurrentTab] = useState(0)
	const [counter, setCounter] = useState(0)

	// TODO REMOVER ESSA BRINCADEIRA AQUI
	const a = () => {
		setTimeout(() => {
			console.log('TIME OUT', counter)
			if (counter === HIGHLIGHT_MOCK.length - 1){
				setCounter(0)
			} else {
				setCounter(counter+1)
			}
		}, 3000)
	}

	useEffect(() => {
		a()
	}, [counter])
	// até aqui -------------------------

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
					<ProfileNavigatorTabs currentTab={currentTab} handleChangeTab={setCurrentTab} />
				</div>
				<div className='profileShowcaseWrapper'>
					<div className='profileShowcaseIntro'>
						<h2>Intro</h2>
						<ul className='profileTabsNavigation'>
							<li>Expert</li>
							<li>Vocalista</li>
						</ul>
					</div>
					<ProfilePost />
				</div>
			</div>
		</>
	)
}

export default Profile
