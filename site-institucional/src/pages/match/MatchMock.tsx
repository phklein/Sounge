import React, { useState, useMemo, useRef } from 'react'
import TinderCard from 'react-tinder-card'

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
    const [aaa, setAaa] = useState<teste[]>([user1, user2])

    const [currentIndex, setCurrentIndex] = useState(aaa.length - 1)
    const [lastDirection, setLastDirection] = useState()

    const currentIndexRef = useRef(currentIndex)
  
    const childRefs: any = useMemo(
        () =>
            Array(aaa.length)
            .fill(0)
            .map((i) => React.createRef()),
        []
    )

    const updateCurrentIndex = (val: any) => {
        setCurrentIndex(val)
        currentIndexRef.current = val
    }
    
    const canGoBack = currentIndex < aaa.length - 1
    const canSwipe = currentIndex >= 0
    
    const swiped = (direction: any, nameToDelete: any, index: any) => {
        console.log(direction)
        setLastDirection(direction)
        updateCurrentIndex(index - 1)
    }
  
    const outOfFrame = (name: any, idx: any) => {
        console.log(`${name} (${idx}) left the screen!`, currentIndexRef.current)
        currentIndexRef.current >= idx && childRefs[idx].current.restoreCard()
    }
  
    const swipe = async (dir: any) => {
        if (canSwipe && currentIndex < aaa.length) {
            await childRefs[currentIndex].current.swipe(dir)
        }
    }
    
    const goBack = async () => {
        if (!canGoBack) return
        const newIndex = currentIndex + 1
        updateCurrentIndex(newIndex)
        await childRefs[newIndex].current.restoreCard()
    }

    return (
        <>
            <div className="container-match">
                <div className="item-match">
                    <nav className="nav-match">

                    </nav>
                </div>
                <div className="item-match">
                    {
                        aaa?.map((match, index) => {
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

                    {
                      currentIndexRef.current < 0 ? (
                        <div className="no-more-cards">
                            <h1>No more cards</h1>
                        </div>
                      ) : null
                    }
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