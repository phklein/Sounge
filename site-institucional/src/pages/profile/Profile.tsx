
import React, { useEffect, useState } from "react"
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
  const [userProfile, setUserProfile] = useState<UserProfileResponseDto>()
  const {id} = useParams()

  const convertImageToByteArray = (image: any) => {
    let reader = new FileReader()

    let fileByArray: any[] = []

    reader.readAsArrayBuffer(image)

    reader.onload = (e) => {
      if (e.target?.readyState == FileReader.DONE) {
        let arrayBuffer = (e.target as FileReader).result as ArrayBuffer
        let array = new Uint8Array(arrayBuffer)

        for (let i = 0; i < array.length; i++) {
          fileByArray.push(array[i])
        }
      }
      
      const reader = new FileReader()

      reader.readAsDataURL(image)
      reader.onload = () => {
        console.log(reader.result)

        // TODO: BRUNA
        const request: PictureUpdateRequestDto = {
          profilePic: reader.result,
          banner: userProfile?.banner
        }

        UserRoute.updateSimplePicture(id, request).then(res => {
          if (res.status === 200) {
            alert('Banner atualizado com sucesso!')
            window.location.reload()
          }
        }).catch(err => {
          console.log(err)
        })
      }
    } 
  }

  const onChange = async (e: React.FormEvent<HTMLInputElement>) => {
    const target = e.target as HTMLInputElement
    
    if (target.files && target.files.length) {
      const file = target.files[0]

      console.log(file)

      convertImageToByteArray(file)
    }
  }

  useEffect(() => {
    const getUserProfile = async () => {
      
      // TODO: BRUNA
      const queryString = window.location.search
      const urlParams = new URLSearchParams(queryString)
      const viewerId = urlParams.get('viewerId')

      if (viewerId) {
        await UserRoute.getProfileForId(id, viewerId).then(res => {
          if (res.status === 200) {
            console.log(res.data)
            setUserProfile(res.data)
          }          
        }).catch(err => {
          console.log(err)
        })

        console.log(userProfile)
      }
    }

    getUserProfile()
  }, [])

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
