import { useEffect, useState } from "react"
import { Button, Form } from 'react-bootstrap'
import { usePromiseTracker } from "react-promise-tracker"

import "../../styles/profile.css"

import UserRoute from "../../routes/UserRoute"

import IUserProfileResponseDto from "../../dto/IUserProfileResponseDto"

import { NavBar } from "../../components/Navbar"
import { BtnSintonizar } from "../../components/BtnSintonizar"
import { PageIntroduction } from "../../components/PageIntroduction"
import { PostPage } from "../../components/PostPage"


export function Profile() {
  const { promiseInProgress } = usePromiseTracker()
  const [userProfile, setUserProfile] = useState<IUserProfileResponseDto>()

  useEffect(() => {
    const queryString = window.location.search
    const urlParams = new URLSearchParams(queryString)
    const code = urlParams.get('id')
    
    if (code) {
      UserRoute.getProfileForId(code).then(res => {
        if (res.status === 200) {
          setUserProfile(res.data)
          console.log(res.data)
        }          
      }).catch(err => {
        console.log(err)
      })
    }
  }, [])

  return (
    <>
      <NavBar isbtnRegisterOff />
      <div className="background-profile">
        <div className="container">
        <div className="profile-container">
          <div className="profile-cap"></div>
          <div className="profile-details">
            <div className="profile-picture">
              <div className="circle-online"></div>
            </div>
            <h2 className="profile-user-name">{userProfile?.name}</h2>
            <h4 className="typography">{userProfile?.description}</h4>
          </div>
          <div className="father-menu">
              <div className="menu-profile">
                <h5 className="typography">Feed</h5>
                <h5 className="typography">Sobre</h5>
                <h5 className="typography">Vídeos</h5>
                <h5 className="typography">Playlist</h5>
                <h5 className="typography">Mais</h5>
                <i className="bx bxs-down-arrow seta-baixo "></i>
              </div>
            <div className="btn-space">
              <BtnSintonizar />
            </div>
          </div>
        </div>
        </div>
      </div>
      <div className="container">
        <div className="profile-container">
          <div className="test">

            <div className="background-intro">
              <h1 className="intro">Habilidades</h1>
                {
                  userProfile?.roles.map(skill => (
                    <PageIntroduction
                      id={skill.id}
                      name={skill.name}
                    />
                  ))
                }
            </div>
            <div className="post-container">
              {/* <Form className="post-add">
                <Form.Group controlId="newPost">
                  <Form.Control type="text" placeholder="O que você está pensando?" />
                </Form.Group>
                <Form.Group controlId="">
                  <Button variant="primary" type="submit">Publicar</Button>
                </Form.Group>
              </Form> */}
              {
                userProfile?.postList.map(post => (
                  <PostPage
                    id={post.id}
                    text={post.text}
                    mediaUrl={post.mediaUrl}
                    hoursPast={post.hoursPast}
                    user={post.user}
                    likeCount={post.likeCount}
                    commentCount={post.commentCount}
                  />
                ))
              }
            </div>
          </div>
        </div>
      </div>
    </>
  )
}
