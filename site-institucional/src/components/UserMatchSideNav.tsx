import UserSimpleResponseDto from "../dto/response/UserSimpleResponseDto"

interface Iprops  {
    contactMatch: UserSimpleResponseDto | undefined;
}

export function UserMatchSideNav(props: Iprops) {
    const { contactMatch } = props

    return (
        <>
            <div className="item-match-nav">
                <p>Maria</p>
            </div>
        </>
    )
 
}