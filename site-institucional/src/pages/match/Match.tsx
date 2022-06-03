
import React, { useState, useMemo, useRef, useEffect } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import TinderCard from 'react-tinder-card'

import './../../styles/tindercards.css'

import UserRoute from "../../routes/UserRoute"
import GroupRoute from "../../routes/GroupRoute"

import { UserMatchResponseDto } from "../../dto/response/UserMatchResponseDto"
import UserProfileResponseDto from '../../dto/response/UserProfileResponseDto'
import UserSimpleResponseDto from '../../dto/response/UserSimpleResponseDto'
import GroupMatchResponseDto from '../../dto/response/GroupMatchResponseDto'

import { ContainerMessage } from '../../components/ContainerMessage'
import { UserMatchSideNav } from '../../components/UserMatchSideNav'
import { GenreNameEnum } from '../../enums/GenreNameEnum'
import { RoleNameEnum } from '../../enums/RoleNameEnum'
import { SexEnum } from '../../enums/SexEnum'
import { SkillLevelEnum } from '../../enums/SkillLevelEnum'

export function Match() {
    const navigate = useNavigate()
    const {id} = useParams()
    
    // setInterval(() => {
    //     UserRoute.getNewMatchs(id).then(res => {
    //         if (res.status === 200) {
    //             alert('Novo match')
    //         }
    //     }).catch(err => {
    //         console.log(err)
    //     })
    // }, 2000)

    const [userProfile, setUserProfile] = useState<UserProfileResponseDto>()

    const [matchList, setMatchList] = useState<UserMatchResponseDto[] | undefined>()
    const [matchGroupList, setMatchGroupList] = useState<GroupMatchResponseDto[] | undefined>()

    const [contactList, setContactList] = useState<UserSimpleResponseDto[] | undefined>()

    let matchListLength: any = 0

    const [currentIndex, setCurrentIndex] = useState(matchListLength - 1)
    const [lastDirection, setLastDirection] = useState()

    const currentIndexRef = useRef(-1)

    const childRefs: any = useMemo(
        () =>
            Array(matchList?.length)
            .fill(0)
            .map((i) => React.createRef()),
        []
    )

    const updateCurrentIndex = (val: any) => {
        setCurrentIndex(val)
        currentIndexRef.current = val
    }
    
    const canGoBack = currentIndex < matchListLength - 1
    const canSwipe = currentIndex >= 0
    
    const swiped = (direction: any, nameToDelete: any, index: any) => {
        setLastDirection(direction)
        updateCurrentIndex(index - 1)
    }

    const outOfFrame = (name: any, idx: any) => {
        console.log(`${name} (${idx}) left the screen!`, currentIndexRef.current)
        currentIndexRef.current >= idx && childRefs[idx].current.restoreCard()
    }
  
    const swipe = async (dir: any) => {
        if (canSwipe && currentIndex < matchList!.length) {
            await childRefs[currentIndex].current.swipe(dir)
        } else {
            console.log('nao funfou')
        }
    }
    
    const goBack = async () => {
        if (!canGoBack) return
        const newIndex = currentIndex + 1
        updateCurrentIndex(newIndex)
        await childRefs[newIndex].current.restoreCard()
    }

    const goToBackPage = () => navigate(-1)

    const isAutomatic: boolean = true

    const parameters: any = isAutomatic ? { 
        id: id,
        maxDistance: 100
    } : { 
        id: id,
        maxDistance: 100,
        minAge: 18,
        maxAge: 50,
        genreName: GenreNameEnum.CLASSICAL,
        roleName: RoleNameEnum.ACCORDIONIST,
        sex: SexEnum.MALE,
        skillLevel: SkillLevelEnum.ADVANCED
    }

    useEffect(() => {
        onkeyup = (e: any) => {
            if (e.keyCode === 37) {
                console.log('key left')
                swipe('left')
            } else if (e.keyCode === 39) {
                console.log('key right')
                swipe('right')
            }
        }

        const getUserProfile = async () => {
            const queryString = window.location.search
            const urlParams = new URLSearchParams(queryString)
            const viewerId = urlParams.get('viewerId')
      
            if (viewerId) {
                await UserRoute.getProfileForId(id, viewerId).then(res => {
                    if (res.status === 200) {
                        setUserProfile(res.data)
                        console.log(res.data)
                    }          
                }).catch(err => {
                    console.log(err)
                })
            }
        }
        
        const getMatchList = async () => {
            if (userProfile?.group !=  null) {
                await UserRoute.getMatchList(parameters).then(res => {
                    if (res.status === 200) {
                        setMatchList(res.data)
                        matchListLength = res.data.length
                        setCurrentIndex(res.data.length)
                        currentIndexRef.current = res.data.length
    
                        console.log(currentIndexRef.current)
                        console.log(currentIndex)
                    }
                }).catch(err => {
                    console.log(err)
                })   
            } else {
                await GroupRoute.getMatchList(parameters).then(res => {
                    if (res.status === 200) {
                        setMatchGroupList(res.data)
                        matchListLength = res.data.length
                        setCurrentIndex(res.data.length)
                        currentIndexRef.current = res.data.length
    
                        console.log(currentIndexRef.current)
                        console.log(currentIndex)
                    }
                }).catch(err => {
                    console.log(err)
                })
            }
        }
        
        const getContactList = async () => {
            await UserRoute.getContactList(id).then(res => {
                if (res.status === 200) {
                    setContactList(res.data)
                    console.log(res.data)
                }
            }).catch(err => {
                console.log(err)
            })
        }

        getUserProfile()
        getContactList()
        getMatchList()
    }, [])

    let isContainerMatchesVisible: boolean = true

    const replaceMatchContent = (name: string) => name === 'match' ? 
    isContainerMatchesVisible = true : isContainerMatchesVisible = false

    return (
        <>
            <div className="container-match">
                <div className="item-match">
                    <nav className="nav-match">
                        <div className="nav-item">
                            <div className="img-perfil" style={{ backgroundImage: `url(${userProfile?.profilePic})` }} />
                            <p>Meu Perfil</p>
                        </div>
                        <div className="nav-item">
                            <i className='bx bxs-chevron-left' onClick={() => goToBackPage()} />
                        </div>
                    </nav>
                    <div className="match-content">
                        <div className="match-nav">
                            <div className="match-li" onClick={() => replaceMatchContent('match')}>
                                <p>Matches</p><div className="number-match-nav">0</div>
                            </div>
                        </div>
                        {
                            isContainerMatchesVisible ? (
                                <div className="container-matchs">
                                    {
                                        contactList?.map((contact) => (
                                            <UserMatchSideNav
                                                contactMatch={contact}
                                            />
                                        ))
                                    }
                                </div>
                            ) : <ContainerMessage />
                        }
                    </div>
                </div>
                <div className="item-match">
                    {
                        matchList != null ?
                            matchList?.map((match, index) => {
                                return (
                                    <TinderCard
                                        ref={childRefs[index]}
                                        className="swipe"
                                        key={match?.id}
                                        preventSwipe={["up", "down"]}
                                        onSwipe={(dir) => swiped(dir, match?.name, index)}
                                        onCardLeftScreen={() => outOfFrame(match?.name, index)}
                                    >
                                        <div
                                            style={{ backgroundImage: `url(${match?.profilePic})` }}
                                            className="card"
                                        >
                                            <h3>{match?.name}</h3>
                                        </div>
                                    </TinderCard>
                                )
                            })
                        : matchGroupList?.map((match, index) => {
                            return (
                                <TinderCard
                                    ref={childRefs[index]}
                                    className="swipe"
                                    key={match?.id}
                                    preventSwipe={["up", "down"]}
                                    onSwipe={(dir) => swiped(dir, match?.name, index)}
                                    onCardLeftScreen={() => outOfFrame(match?.name, index)}
                                >
                                    <div
                                        style={{ backgroundImage: `url(${match?.profilePic})` }}
                                        className="card"
                                    >
                                        <h3>{match?.name}</h3>
                                    </div>
                                </TinderCard>
                            )
                        })
                    } 

                    {/* {
                      currentIndexRef.current < 0 ? (
                        <div className="no-more-cards">
                            <h1>No more cards</h1>
                        </div>
                      ) : null
                    } */}
                    <div className='buttons'>
                        <button onClick={() => swipe('left')}><i className='bx bx-x'></i></button>
                        <button onClick={() => goBack()}><i className='bx bx-undo'></i></button>
                        <button onClick={() => swipe('right')}><i className='bx bxs-heart'></i></button>
                    </div>
                </div>
            </div>
                
        {/* {lastDirection ? (
            <h2 key={lastDirection} className='infoText'>
            You swiped {lastDirection}
            </h2>
        ) : (
            <h2 className='infoText'>
            Swipe a card or press a button to get Restore Card button visible!
            </h2>
        )}          */}
        </>
    )
}