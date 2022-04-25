import "../styles/postpage.css"

import IUserSimpleResponseDto from "../dto/IUserSimpleResponseDto"

interface Iprops {
  id: number;
  text: string;
  mediaUrl?: string;
  hoursPast: number;
  user: IUserSimpleResponseDto;
  likeCount: number;
  commentCount: number;
}

export function PostPage(props: Iprops) {
  const {id, text, mediaUrl, hoursPast, user, likeCount, commentCount} = props

  const style = {
    backgroundImage: `url(${mediaUrl})`,
    height: `${mediaUrl != null || mediaUrl != undefined ? "350px"  : "0px"}`,
    width: `100%`,
    backgroundColor: `#1B1B1B`
  }

  return (
    <>
      <div className="background-post">
        <div className="profile-details-post">
          <img className="profile-picture-post"></img>
          <p className="profile-user-name-post">{user.name}</p>
        </div>
        <p className="profile-description-post">{text}</p>
        <div className="profile-image-post" style={style} />
        <div className="background-post-wrapper">
          <p>{likeCount} curtidas</p>
          <p>{commentCount} comentários</p>
        </div>
      </div>
    </>
  );
}
