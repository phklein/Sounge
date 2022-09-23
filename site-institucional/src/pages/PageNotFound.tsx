import { useNavigate, Link } from 'react-router-dom'


import { NavBar } from '../components/Navbar'

import '../styles/not-found.css'

export function PageNotFound() {
    return (
        <div className="page-not-found">
            <NavBar />
            <section className="notfound-container">
            
                <h1>Página não encontrada</h1>
                <div className="imagem"></div>
            </section>
  
        </div>
    )
} 