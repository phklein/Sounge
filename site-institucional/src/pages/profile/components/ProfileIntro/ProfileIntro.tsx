import React from "react"
import "./ProfileIntro.style.css"

export const PROFILE_TYPE = {
	USER: 0,
	BAND: 1,
}

const ProfileSkillInfo = ({ label = "", Icon = () => {} }: { label: ""; Icon: any }) => {
	return (
		<li className='profileShowcaseIntroSkill'>
			<Icon fontSize='large' />
			<span>{label}</span>
		</li>
	)
}

const ProfileBandInfo = ({
	leader = false,
	imageSrc = "",
	name = "",
	role = "",
	handleClick = () => {},
}: {
	leader?: boolean
	imageSrc: string
	name: string
	role: string
	handleClick?: Function
}) => {
	return (
		<li className='profileShowcaseIntroBandWrapper'>
			<a className='profileShowcaseIntroBand'>
				<img src={imageSrc} />
				<div className='profileShowcaseIntroBandInfo'>
					<span className='profileShowcaseIntroBandInfoName'>
						{leader ? (
							<span>
								{name} <span className='profileShowcaseIntroBandInfoLeader'>{`(Líder da Banda)⭐`}</span>
							</span>
						) : (
							name
						)}
					</span>
					<span className='profileShowcaseIntroBandInfoRole'>{role}</span>
				</div>
			</a>
		</li>
	)
}

const ProfileIntro = ({ infos = [], type = PROFILE_TYPE.USER }: { infos: any[]; type: number }) => {
	return (
		<div className='profileShowcaseIntro'>
			<h2>Intro</h2>
			<ul className='profileLists'>
				{infos.map((info) => {
					return type === PROFILE_TYPE.USER ? (
						<ProfileSkillInfo label={info.label} Icon={info.icon} />
					) : (
						<ProfileBandInfo imageSrc={info.imageSrc} name={info.name} role={info.role} leader={info.leader} />
					)
				})}
				<li></li>
			</ul>
		</div>
	)
}

export default ProfileIntro
