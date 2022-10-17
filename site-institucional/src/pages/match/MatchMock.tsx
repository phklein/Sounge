
import React, { useState, useMemo, useRef, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import TinderCard from 'react-tinder-card'

import { ContainerMessage } from '../../components/ContainerMessage'
import { UserMatchSideNav } from '../../components/UserMatchSideNav'

import './../../styles/tindercards.css'

export interface teste {
    key: number,
    name: string,
    profilePic: string
}

const user1: teste = {
    key: 1,
    name: "João",
    profilePic: "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=ting&dpr=2&h=650&w=940"
}

const user2: teste = {
    key: 2,
    name: "Maria",
    profilePic: "https://veja.abril.com.br/wp-content/uploads/2016/06/alx_maria_bethania3_original.jpeg"
}

export function MatchMock() {    
    const navigate = useNavigate()

    const [users, setUsers] = useState<teste[]>([user1, user2])

    const [currentIndex, setCurrentIndex] = useState(users.length - 1)
    const [lastDirection, setLastDirection] = useState()

    const currentIndexRef = useRef(currentIndex)
  
    const childRefs: any = useMemo(
        () =>
            Array(users.length)
            .fill(0)
            .map((i) => React.createRef()),
        []
    )

    const updateCurrentIndex = (val: any) => {
        setCurrentIndex(val)
        currentIndexRef.current = val
    }
    
    const canGoBack = currentIndex < users.length - 1
    const canSwipe = currentIndex >= 0
    
    const swiped = (direction: any, nameToDelete: any, index: any) => {
        console.log(direction)
        setLastDirection(direction)
        updateCurrentIndex(index - 1)
        console.log(currentIndexRef.current)
    }
  
    const outOfFrame = (name: any, idx: any) => {
        console.log(`${name} (${idx}) left the screen!`, currentIndexRef.current)
        currentIndexRef.current >= idx && childRefs[idx].current.restoreCard()
    }
  
    const swipe = async (dir: any) => {
        if (canSwipe && currentIndex < users.length) {
            console.log(currentIndex)
            await childRefs[currentIndex].current.swipe(dir)
        }
    }
    
    const goBack = async () => {
        if (!canGoBack) return
        const newIndex = currentIndex + 1
        updateCurrentIndex(newIndex)
        await childRefs[newIndex].current.restoreCard()
    }

    const goToBackPage = () => {
        navigate(-1)
    }

    useEffect(() => {
        const buttons = document.getElementsByClassName('buttons')

        onkeyup = (e: any) => {
            if (e.keyCode === 37) {
                console.log('key left')
                swipe('left')
            } else if (e.keyCode === 39) {
                console.log('key right')
                swipe('right')
            }
        }

        if (users.length > 0) {
            for (let i = 0; i < buttons.length; i++) {
                const button = buttons[i] as HTMLElement
                button.style.opacity = '1'
                button.style.pointerEvents = 'auto'
            }
        }

        console.log(currentIndex)
        console.log(childRefs)
    }, [])

    let isContainerMatchesVisible: boolean = true

    const replaceMatchContent = (name: string) => {
        if (name === 'match') {
            isContainerMatchesVisible = true            
        } else if (name === 'userMatchPost') {
            isContainerMatchesVisible = false
        }
    }

    const returnNoMoreCards = () => {
        const btnHeart = document.getElementsByClassName('bxs-heart')

        for (let i = 0; i < btnHeart.length; i++) {
            const btnHrt = btnHeart[i] as HTMLElement
            btnHrt.style.opacity = '0.3'
        }

        return (
            <div className="no-more-cards">
                <h1>No more cards</h1>
            </div>
        )
    }


    return (
        <>
            <div className="container-match">
                <div className="item-match">
                    <nav className="nav-match">
                        <div className="nav-item">
                            <div className="img-perfil" style={{ backgroundImage: `url(${user1.profilePic})` }} />
                            <p>Meu Perfil</p>
                        </div>
                        <div className="nav-item">
                            <i className='bx bxs-chevron-left' onClick={() => goToBackPage()}></i>
                        </div>
                    </nav>
                    <div className="match-content">
                        <div className="match-nav">
                            <div className="match-li" onClick={() => replaceMatchContent('match')}>
                                <p>Matches</p><div className="number-match-nav">0</div>
                            </div>
                            {/* <div className="match-li" onClick={() => replaceMatchContent('userMatchPost')}>
                                <p>Mensagens</p><div className="number-match-nav">25</div>
                            </div> */}
                        </div>
                        {
                            isContainerMatchesVisible ? (
                                <div className="container-matchs">
                                    {/* <UserMatchSideNav /> */}
                                </div>
                            ) : (
                                <ContainerMessage />
                            )
                        }
                    </div>
                </div>
                <div className="item-match">
                    {
                        users?.map((match, index) => {
                            return (
                                <TinderCard
                                    ref={childRefs[index]}
                                    className="swipe"
                                    key={match.key}
                                    preventSwipe={["up", "down"]}
                                    onSwipe={(dir) => swiped(dir, match.name, index)}
                                    onCardLeftScreen={() => outOfFrame(match.name, index)}
                                >
                                    <div
                                        style={{ backgroundImage: `url(${match.profilePic})` }}
                                        className="card"
                                    >
                                        <h3>{match.name}</h3>
                                    </div>
                                </TinderCard>
                            )
                        })
                    } 
                    { currentIndexRef.current < 0 ? returnNoMoreCards() : null }
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