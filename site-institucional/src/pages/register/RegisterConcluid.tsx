
import LogoVertical from '../../assets/img/logo-texto-vertical.png'

import '../../styles/register.css'

interface Iprops {
    signIn: () => void
}

export function RegisterConcluid(props: Iprops) {

    const {signIn} = props

    return (
        <>
        <div className="container-concluid">
            <img src={LogoVertical} alt="" />
            <h1>Tudo Pronto!</h1>
            <button onClick={signIn} className="btn-sign">Comece a procurar!</button>
        </div>
        </>
    )
}
