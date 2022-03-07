import { useNavigate, Link } from 'react-router-dom'

import { Button } from './Button'

export function NavBar(props: any) {
    return (
        <header>
            <nav className="container navbar">
                <img src="https://s3-alpha-sig.figma.com/img/e68c/cd7e/cf67a34866a0ad05813a407693875fe9?Expires=1647216000&Signature=B5bOvM-FkAYOuLio-GRQK~sQ6-N296wbtEZ8wOBAsctz~L~ZwHFJeKle~cyV8BkvbQ8rt0jHqJcgQcnEmkX-p4LZcvd6FPjh~ApAfpU-hnwjibbTXXaMkb5H~4BtkugiCu8u0xWiy5kGxjG-EY7UefrTCRU9oxZLbA5Cjgdy-PPo7XHj-bAnCTP7GwnZXMHYHD-rZN5kgFMXyQhsnS4YAYMmtjooN58cSx~FdxSSKNICbCmDnadRwBaVeQMf-kQ5Ts8WER38QR20SgNA-TxEzXpGMpprBD4FjX68SgXbGtvlnnbPX5PW0NI76FegGePSqi1GpVc2cGVbgcHUCfKgfw__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA" alt="" />
                <ul>
                    <li><Link to="/page-not-found" className="link-navbar">Inicio</Link></li>
                    <li><Link to="/page-not-found" className="link-navbar">Blog</Link></li>
                    <li><Link to="/page-not-found" className="link-navbar">Sobre</Link></li>
                    <li><Link to="/page-not-found" className="link-navbar">Membros</Link></li>
                    <li><Link to="/page-not-found" className="link-navbar">Fórum</Link></li>
                </ul>
                <Button>Cadastre-se</Button>   
            </nav>
        </header>
    )
}