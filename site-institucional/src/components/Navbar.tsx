import { useNavigate, Link } from 'react-router-dom'

import { Buttonaa } from './Buttonaa'

export function NavBar(props: any) {
    return (
        <header>
            <nav className="container navbar">
                <img src="https://s3-alpha-sig.figma.com/img/b95e/e1ed/e10c46f9e02772cb19fa8c255a122fc8?Expires=1648425600&Signature=S-98rq30aNE4O5f6OVS4Qj9Rxtl7ZiANQsU30GIW1dwu~DzsFVRJcxE~yJZAgYs8040sZTRP08j4QVbSVLVq40jBDZeHAN4qhjoIPfcYM1iXholvsKVpRE0z0EnMOPYlH0LY2C4e~pivLp9Tgu904mVc0epWJG2mZ5PINLfUFNoN8TaLgz9-SJq9HEYExRDUw80d0vkk67B9KHSq8WmB7lGX-I6vyuXGCLFd3ThLk5EIVF~LiUZDPpEhUh5KzFMv5rYl0Toi-Lc-C1TpimUI6g0zs5MWeKzzmlecWW6CAQxQI10ziVxDWhayBXGKnJvcxdajRbxM1MsXN0VQTG2s9Q__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA" alt="" />
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