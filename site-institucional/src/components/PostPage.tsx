import { useEffect } from "react"
import { Favorite, FavoriteBorder, Comment } from "@mui/icons-material"
import IUserSimpleResponseDto from "../dto/response/UserSimpleResponseDto"
import "../styles/postpage.css"

interface Iprops {
	key: number
	text: string
	mediaUrl?: string
	hoursPast: number
	user: IUserSimpleResponseDto
	likeCount: number
	commentCount: number
}

export function PostPage(props: Iprops) {
	const { key, text, mediaUrl, hoursPast, user, likeCount, commentCount } = props

	return (
		<>
			<div className='background-post'>
				<div className='profile-details-post'>
					<img className='profile-picture-post' src={`data:image/png;base64,${user.profilePic}`} />
					<p className='profile-user-name-post'>{user.name}</p>
				</div>
				<p className='profile-description-post'>{text}</p>
				{mediaUrl && <img className='profile-image-post' src={`data:image/png;base64,${mediaUrl}`} />}
				<div className='background-post-wrapper'>
					<p className='profileShowcaseIcon'>
						<FavoriteBorder sx={{ marginRight: "8px", fill: "var(--red)" }} /> {likeCount} curtidas
					</p>
					<p className='profileShowcaseIcon'>
						<Comment sx={{ marginRight: "8px", fill: "var(--blue)" }} />
						{commentCount} comentários
					</p>
				</div>
			</div>
		</>
	)
}
