import { useEffect, useState } from "react"

import UserRoute from "../routes/UserRoute"

import UserSimpleResponseDto from "../dto/response/UserSimpleResponseDto"
import UserProfileResponseDto from "../dto/response/UserProfileResponseDto"

interface Iprops  {
    contactMatch: UserSimpleResponseDto | undefined;
}

export function UserMatchSideNav(props: Iprops) {
    const { contactMatch } = props

    const [userProfile, setUserProfile] = useState<UserProfileResponseDto>()

    useEffect(() => {
        UserRoute.getProfileForId(contactMatch?.id, contactMatch?.id).then(res => {
            if (res.status === 200) {
                setUserProfile(res.data)
            }
        })
    }, [])

    return (
        <>  
            {
                userProfile?.profilePic != null ? (
                    <div 
                        className="item-match-nav" 
                        style={
                            { 
                                backgroundImage: `url(${userProfile?.profilePic})`, 
                                backgroundSize: 'cover', 
                                backgroundPosition: 'center',
                                boxShadow: 'inset 0px 0px 50px rgba(0,0,0,0.8)',
                                borderRadius: '5px',
                            }
                        }
                    >
                        <p>
                            {userProfile?.name}
                            <h3>***telefone***</h3>
                        </p>
                    </div>
                ) : (
                    <div 
                        className="item-match-nav" 
                        style={
                            { 
                                backgroundImage: `url(https://i.pinimg.com/736x/c9/e3/e8/c9e3e810a8066b885ca4e882460785fa.jpg)`, 
                                backgroundSize: 'cover', 
                                backgroundPosition: 'center',
                                boxShadow: 'inset 0px 0px 50px rgba(0,0,0,0.8)',
                                borderRadius: '5px',
                            }
                        }
                    >
                        <p>
                            {userProfile?.name}
                            <h3>***telefone***</h3>
                        </p>
                    </div>
                )
            }
        </>
    )
 
}