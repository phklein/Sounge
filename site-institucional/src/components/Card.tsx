
import './../styles/tindercards.css'

import TinderCard from 'react-tinder-card'

import { UserMatchResponseDto } from '../dto/response/UserMatchResponseDto'

interface Iprops  {
    userMatch: UserMatchResponseDto | undefined
}

export function Card(props: Iprops) {
    const { userMatch } = props

    const swiped = (direction: any, nameToDelete: any) => {
        console.log("removing: " + nameToDelete)
        console.log(direction)
    }

    const outOfframe = (name: any) => {
        console.log(name + " left the screen!")
    }

    return (
        <>
            <div className="tinderCards">
                <div className="tinderCards_cardContainer">
                    <TinderCard
                        className="swipe"
                        key={userMatch!.id}
                        preventSwipe={["up", "down"]}
                        onSwipe={(dir) => swiped(dir, userMatch!.name)}
                        onCardLeftScreen={() => outOfframe(userMatch!.name)}
                    >
                        <div
                            style={{ backgroundImage: `url(${userMatch!.profilePic})` }}
                            className="card"
                        >
                            <h3>{userMatch!.name}</h3>
                        </div>
                    </TinderCard>
                </div>
            </div>
        </>
    )
}