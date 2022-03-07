import { useNavigate, Link } from 'react-router-dom'

import '../styles/home.css'

import { Button } from '../components/Button'
import { NavBar } from '../components/Navbar'

export function Home() {
    return (
        <div>
            <NavBar />
            <section className="banner">
                <h1>“Tocar uma nota equivocada é insignificante.<br />Tocar sem paixão é imperdoável”</h1>
                <p>Ludwing Van Beethoven</p>
            </section>
            <section className="player">
                <h1>Compartilhe seu som!</h1>
                <iframe className="container" src="https://open.spotify.com/embed/playlist/2M2aj5IeLoaNlu4wgNxvcH?utm_source=generator" width="100%" height="100%" frameBorder="0" allowFullScreen={true} allow="autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture"></iframe>
            </section>
        </div>
    )
}