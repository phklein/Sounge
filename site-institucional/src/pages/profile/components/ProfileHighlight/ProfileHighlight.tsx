import React from "react"
import "./ProfileHighlight.style.css"

const ProfileHighlight = ({
	bannerSrc = 'https://i.pinimg.com/originals/70/c3/26/70c326bf1a4214a492a69d4a7f6d91b8.jpg',
	avatarSrc = 'https://agenciauva.files.wordpress.com/2018/08/otakus-features-origin-and-types.jpg',
	userInfo = { name: "Elvis Presley", description: "Rei do Rock'n'Roll" },
}) => {
	return (
		<div className='profileHighlightWrapper'>
			<img className='profileHighlightBanner' src={bannerSrc} />
			<div className='profileHighlightUser'>
				<img className='profileHighlightUser_pic' src={avatarSrc} />
				<h3>{userInfo.name}</h3>
				<span>{userInfo.description}</span>
			</div>
		</div>
	)
}

export default ProfileHighlight
