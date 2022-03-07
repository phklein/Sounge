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
            <section className="events">
                <h1>Os eventos mais esperados</h1>
                <div className="container events-list">
                    <div className="events-item">
                        <img src="https://s3-alpha-sig.figma.com/img/2354/9cd1/a912ed95b5a7fd23ca621963af2d2a9c?Expires=1647820800&Signature=L6UM0NJRa8ImGKfNj8BoP6bwZHGge26~CpSmLd91-FJyxnVNy3MErcaxZwdQYNnbdsw5R2ed5NHfOvEVbNEN6Ab6QiXzf8~SLIFKTA9j1skqTZvBl3PQQa64C0ud6TpSiSqtxxo-dgYKFstFPF9R~Cb09wD7BgqBZ4o1zC-l6NxA8HtCRTgiHEoszCk4XNiAKLJ9IWCGmnKapMcT15UhhwBanoHOBy3HVAw~KtOyYVWt-l5dOsOZ3yRBbtiETemXMrQxMkTmnynizFLwAQ7xgaOQ4jUMRBJMll33KrlWW29s9gtM7Yqni3NCr4YlblJ8C7I5-VHbZoIzQEi4E0o7WA__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA" alt="" />
                        <h2>Lollapaloza Brazil</h2>
                        <p>qui., 03 de maio</p>
                        <Button>rspv</Button>
                    </div>
                    <div className="events-item">
                        <img src="https://s3-alpha-sig.figma.com/img/9360/fd96/ddd1caf453417c91aea0b47d19172dcf?Expires=1647820800&Signature=OMxtAel6JNbicIGSuxJ7fOvMa0gjLGAP38RtuWme~LpQtnqakb50MJhfLMd-WKuzLbYnmbk8Qc7bKR06PXma7zMvKv3Cc~ckafIGK-rKeTEx3CeflOPWrWVAhYjljDYmgtZHFCpBCdLuahYd8TBLDr3kjbxbUkxXkpnCry-qULC4KMbOQoLfyWg6lto1jqciIE77rr-NIU4aOVXx2mXneM5WW8AlM2hdV0CQexUMbXpOm6XJ9i9Pdpun1g5wWmwwc6CcxbA95al1Xg5B-mWsxtAwomdGvIUTEzjkDiXhlEJ17JcJjVfUflXunyCFdR80pbxRei2dToSFl66wuLF5Mg__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA" alt="" />
                        <h2>Rock in Rio</h2>
                        <p>sáb., 20 de jan.</p>
                        <Button>rspv</Button>
                    </div>
                    <div className="events-item">
                        <img src="https://s3-alpha-sig.figma.com/img/04bd/4f56/d5f6610c97d6dba960ed8d232cccfe8e?Expires=1647820800&Signature=HN1M9b2mdFsVW2r-rqPUtR195AKR7o4wXZNcFEzmGFgsfgqpv9KA2rjSAuLTfQr-ctOZo3U-TA4da49eRWRk4LXNqjFGYMtlXlWFBJRpyyoEgIOPXnVe1HGkurAzL5~Rt9POlOW3Y0bSc-tg0bV51xEj9RmwuKJZ9OGVJDIDmR3Fu2O73Wl-mAJckiRFIRLAeFOV0-tOlzFOlG84o0zgBbFhs7zjgNHU6O8NN8sv39tg62nb4BbULzq4ICBzrGvwYk7sB9BasXV-SRBw2iEIQwWjyClqUrij8shplnNYZCdEYO5TngqdwmD8oTK6Aocyir2tqXD1ksbX9VO7JLqLhg__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA" alt="" />
                        <h2>Coachella</h2>
                        <p>sáb., 10 de agosto</p>
                        <Button>rspv</Button>
                    </div>
                </div>
            </section>
        </div>
    )
}