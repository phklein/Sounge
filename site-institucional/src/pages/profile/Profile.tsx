import React, { useEffect, useState } from "react"
import { usePromiseTracker } from "react-promise-tracker"
import {useParams} from "react-router-dom"

import "../../styles/profile.css"

import UserRoute from "../../routes/UserRoute"

import PictureUpdateRequestDto from "../../dto/request/PictureUpdateRequestDto"
import UserProfileResponseDto from "../../dto/response/UserProfileResponseDto"

import { NavBar } from "../../components/Navbar"
import { BtnSintonizar } from "../../components/BtnSintonizar"
import { PageIntroduction } from "../../components/PageIntroduction"
import { PostPage } from "../../components/PostPage"

export function Profile() {
  const { promiseInProgress } = usePromiseTracker()

  const [userProfile, setUserProfile] = useState<UserProfileResponseDto | undefined>()
  const {id} = useParams()

  useEffect(() => {
    const queryString = window.location.search
    const urlParams = new URLSearchParams(queryString)

    const viewerId = urlParams.get('viewerId')

    if (viewerId) {
      UserRoute.getProfileForId(id, viewerId).then(res => {
        if (res.status === 200) {
          setUserProfile(res.data)
        }          
      }).catch(err => {
        console.log(err)
      })
    }
  }, [])

  // const onChange = (e: React.FormEvent<HTMLInputElement>) => {
  //   const target = e.target as HTMLInputElement
    
  //   if (target.files && target.files.length) {
  //     const file = target.files[0]

  //     console.log(file)
      
  //     postImage(file).then(url => {
  //       console.log(url)

  //       const request: PictureUpdateRequestDto = {
  //         profilePic: `${userProfile?.profilePic}`,
  //         banner: url
  //       }
    
  //       UserRoute.updatePicture(id, request).then(res => {
  //         if (res.status === 200) {
  //           alert('Banner atualizado com sucesso!')
  //         }
  //       }).catch(err => {
  //         console.log(err)
  //       })
  //     })
  //   }
  // }

  // const postImage = async (file: File) => {
  //   const formData = new FormData()
  //   formData.append('image', file)

  //   console.log(formData)

  //   const response = await fetch('https://api.imgur.com/3/image/', {
  //     method: 'POST',
  //     headers: {
  //         Authorization: 'Client-ID c10e4a345abd5fe'
  //     },
  //     body: formData
  //   })
    
  //   const json = await response.json()

  //   console.log(json)
  //   return json.data.link
  // }

  const onChange = async (e: React.FormEvent<HTMLInputElement>) => {
    const target = e.target as HTMLInputElement
    
    if (target.files && target.files.length) {
      const file = target.files[0]

      console.log(file)

      const request: PictureUpdateRequestDto = {
        profilePic: userProfile?.profilePic,
        banner: await file.arrayBuffer()
      }
    
      UserRoute.updateSimplePicture(id, request).then(res => {
        if (res.status === 200) {
          alert('Banner atualizado com sucesso!')
        }
      }).catch(err => {
        console.log(err)
      })
    }
  }

  return (
    <>
      <NavBar isbtnRegisterOff />
      <div className="background-profile">
        <div className="container">
        <div className="profile-container">
          <div className="profile-cap" style={{backgroundSize: 'cover', backgroundPosition: 'center' ,backgroundImage: `url(${userProfile?.banner})`}}>
            <div className="btn-update-picture">aaaaaaa</div>
          </div>
          <div className="profile-details">
            <div className="profile-picture" style={{backgroundImage: `url(${userProfile?.profilePic})`}}>
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
                <i className="bx bxs-down-arrow seta-baixo"></i>
              </div>
            <div className="btn-space">
              <BtnSintonizar />
            </div>
          </div>
        </div>
        </div>
      </div>
      <input 
        type="file"
        name="banner" 
        id="banner" 
        accept="image/png, image/jpeg"
        onChange={onChange}
      />
      <div className="container">
        <div className="profile-container">
          <div className="test">

            <div className="background-intro">
              <h1 className="intro">Habilidades</h1>
                {
                  userProfile?.roles.map(skill => (
                    <PageIntroduction
                      key={skill.id}
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
                    key={post.id}
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
