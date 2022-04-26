import { useNavigate, Link } from 'react-router-dom'

import LogoHorizontal from '../assets/img/logo-fundo-escuro-texto-horizontal.png'
import UserRoute from '../routes/UserRoute';

import { Buttonaa } from './Buttonaa'

interface Iprops {
    isbtnRegisterOff?: boolean;
}

export function NavBar(props: Iprops) {
    const { isbtnRegisterOff } = props

    const navigate = useNavigate()

    const logout = () => {
        const queryString = window.location.search
        const urlParams = new URLSearchParams(queryString)
        const code = urlParams.get('id')
        
        if (code) {
            UserRoute.logout(code).then(res => {
                if (res.status === 200) {
                    navigate('/')
                }          
            }).catch(err => {
                console.log(err)
            })
        }
            
    }

    if (isbtnRegisterOff) {
        return (
            <header>
                <nav className="container navbar">
                    <img src={LogoHorizontal} alt="" />
                    <ul>
                        <li><Link to="/page-not-found" className="link-navbar">Inicio</Link></li>
                        <li><Link to="/page-not-found" className="link-navbar">Blog</Link></li>
                        <li><Link to="/page-not-found" className="link-navbar">Sobre</Link></li>
                        <li><Link to="/page-not-found" className="link-navbar">Membros</Link></li>
                        <li><Link to="/page-not-found" className="link-navbar">Fórum</Link></li>
                    </ul>
                    <Buttonaa onClick={logout}>Sair</Buttonaa>
                </nav>
            </header>
        )
    } else {
        return (
            <header>
                <nav className="container navbar">
                    <img src={LogoHorizontal} alt="" />
                    <ul>
                        <li><Link to="/page-not-found" className="link-navbar">Inicio</Link></li>
                        <li><Link to="/page-not-found" className="link-navbar">Blog</Link></li>
                        <li><Link to="/page-not-found" className="link-navbar">Sobre</Link></li>
                        <li><Link to="/page-not-found" className="link-navbar">Membros</Link></li>
                        <li><Link to="/page-not-found" className="link-navbar">Fórum</Link></li>
                    </ul>
                    <Link to="/register"><Buttonaa>Cadastre-se</Buttonaa></Link>
                </nav>
            </header>
        )
    }
}