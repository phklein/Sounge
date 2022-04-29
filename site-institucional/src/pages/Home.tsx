import React, { useEffect } from 'react'
import { useNavigate, Link } from 'react-router-dom'
// import Swal from 'sweetalert2'

import '../styles/home.css'

import EventOne from '../assets/img/event-one.png'
import EventTwo from '../assets/img/event-two.png'
import EventThree from '../assets/img/event-three.png'
import PubOne from '../assets/img/pub-one.png'
import PubTwo from '../assets/img/pub-two.png'
import PubThree from '../assets/img/pub-three.png'

import { NavBar } from '../components/Navbar'
import { Footer } from '../components/Footer'
import { Buttonaa } from '../components/Buttonaa'

const cardStyles = { 
    publicationStyle1: { background: `url(${PubOne})`, backgroundSize: 'cover', backgroundPosition: 'center' },
    publicationStyle2: { background: `url(${PubTwo})`, backgroundSize: 'cover', backgroundPosition: 'center' },
    publicationStyle3: { background: `url(${PubThree})`, backgroundSize: 'cover', backgroundPosition: 'center' }
}

export function Home() {

    useEffect(() => {
        console.log(cardStyles)
    }, [])

    return (
        <div className="page-init">
            <NavBar />
            <section className="banner">
                <h1>“Tocar uma nota equivocada é insignificante.<br />Tocar sem paixão é imperdoável”</h1>
                <p>Ludwing Van Beethoven</p>
                <Link to="/login"><button className="btn-entrar">Entrar</button></Link>   
            </section>
            <section className="player">
                <h1>Compartilhe <span>seu som!</span></h1>
                <iframe className="container" src="https://open.spotify.com/embed/playlist/2M2aj5IeLoaNlu4wgNxvcH?utm_source=generator" width="100%" height="100%" frameBorder="0" allowFullScreen={true} allow="autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture"></iframe>
            </section>
            <section className="events">
                <h1>Os eventos mais esperados</h1>
                <div className="container events-list">
                    <div className="events-item">
                        <img src={EventOne} alt="" />
                        <h2>Lollapaloza Brazil</h2>
                        <p>qui., 03 de maio</p>
                        <Buttonaa>rspv</Buttonaa>
                    </div>
                    <div className="events-item">
                        <img src={EventTwo} alt="" />
                        <h2>Rock in Rio</h2>
                        <p>sáb., 20 de jan.</p>
                        <Buttonaa>rspv</Buttonaa>
                    </div>
                    <div className="events-item">
                        <img src={EventThree} alt="" />
                        <h2>Coachella</h2>
                        <p>sáb., 10 de agosto</p>
                        <Buttonaa>rspv</Buttonaa>
                    </div>
                </div>
            </section>
            <section className="publications">
                <h1>Veja publicações do momento</h1>
                <div className="container publications-list">
                    <div className="publication-item" style={cardStyles.publicationStyle1}></div>
                    <div className="publication-item" style={cardStyles.publicationStyle2}></div>
                    <div className="publication-item" style={cardStyles.publicationStyle3}></div>
                </div>
            </section>
            <Footer />
        </div>
    )
}