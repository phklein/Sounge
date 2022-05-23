import React, { useEffect, useState } from "react"
import {useParams} from "react-router-dom"

import './../../styles/tindercards.css'

import UserRoute from "../../routes/UserRoute"

import { Card } from "../../components/Card"

import { UserMatchResponseDto } from "../../dto/response/UserMatchResponseDto"

export function Match() {
    const {id} = useParams()
    
    const [matchList, setMatchList] = useState<UserMatchResponseDto[] | undefined>()
    
    const isAutomatic: boolean = true
    const parameters: any = isAutomatic ? { id: id, maxDistance: 100 } : { id: id }

    useEffect(() => {
        UserRoute.getMatchList(parameters).then(res => {
            if (res.status === 200) {
                setMatchList(res.data)
                console.log(res.data)
            }
        }).catch(err => {
            console.log(err)
        })
    }, [])

    return (
        <>
            <div className="tinderCards">
                <div className="tinderCards_cardContainer">
                    {
                        matchList?.map(match => {
                            return (
                                <Card
                                    key={match.id}
                                    userMatch={match}
                                />
                            )
                        })
                    }
                </div>
            </div>           
        </>
    )
}