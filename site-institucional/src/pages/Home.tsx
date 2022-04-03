import { useNavigate, Link } from 'react-router-dom'

import '../styles/home.css'

import { NavBar } from '../components/Navbar'
import { Footer } from '../components/Footer'
import { Button } from '../components/Button'

const cardStyles = { 
    publicationStyle1: { background: 'url(https://s3-alpha-sig.figma.com/img/5bdd/3129/8f7dc0725f4efc3daf9b18b0d1e93303?Expires=1647820800&Signature=N8ql0T470F~7gWehiWIuptBvg~q5TiRajyoq3r4LUTKIo0YKBopibruCUSOGCuwK8ZyIzEpkruc5N9MBUN7i1SMt3yoULfbLfLtJ2r3cf8uGSbBF-1zbyzdGG5v4Awo12MgIENSeFJJ675IBrZAedWgeWa0A1jjtqXa94tcrPzDU13pABkt27~85d8rsXLYzu-PQ6n7in--X2mzDziIsqKMQGcrGYXF-iM0EadxTvJ8yxOUvGjDOi-K7nClaZ8v4sdvKwobMLER-nv8r3zZlQ50r6-XdwqWdZ9n~v85VOZX970BPlx~VBBYhowxjkzcsWBUA0J9K7M0GhAYjBLaoQA__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA)' },
    publicationStyle2: { background: 'url(https://s3-alpha-sig.figma.com/img/1f69/9ff5/f8d9c435c1258b95b7ea8680c283f9eb?Expires=1647820800&Signature=hH~7gDj6pQsmIPRS3lpDqXIjCgovf-T~Gf-W4mqsHjrUOchN-qDI3fFM6s3ZENRTeqnYMnyvf-PoKbgKB6DvuZd9wk4rli~Ty5~vCSPX50U9q-bJSaQfaZIH51DVBezNRV-S6gHrWBnkXFMnA3Vg1iONGUBdbZafNL3lMwOpkRThscwI-5AD0eSFyAo3IV9~hZhXsyvzTCxjQer-bLsF9U0tS3tTT3EVKIJgnByf48RdJgizmXtbeV-s9QVJxq-8R1qZTV4-09nJngwpCqJwuxUEaIVAPmmxgh1sPESD1iWrakYp96ujgoQ-Y-TC~O7OwV~3adG3RHopdO8b49f3Dg__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA)' },
    publicationStyle3: { background: 'url(https://s3-alpha-sig.figma.com/img/6698/f5f7/6b2416b1dd205bc9d60d88ecb8dae7ce?Expires=1647820800&Signature=fbYyJmohH-oTbwoeYTI3mAYGV-sTfQ5zIRxEQUYrz9neMYALDhDO2z0k0~RwDN5K0tt5DDoHcwMjQjOkURSxnhsNZwmoLWnXnLGNdNB9Jo~Rd4DYtJGYW5GPY6i3tSkk~gBTPYVDTizdfFKEi3BlSIeTJ6iEP2YXDEcbJqREbyhhXpB0ei78Zq8pXGEDdxIl5PmYLWygkneMIfLtdUvUmuLHxeLBW80dkQAqqTxqWW276XW-5oQ4ZSHm-wd8iRCUfQECjgkAoyWBFidyMfzqKcM7CPJWhaSl4vS-2eHni~iz~idqVBq4FWb5yixBDhpCCP3x9LfKMaUmoIK8wM~zzQ__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA)' }
}

export function Home() {
    return (
        <div>
            <NavBar />
            <section className="banner">
                <h1>“Tocar uma nota equivocada é insignificante.<br />Tocar sem paixão é imperdoável”</h1>
                <p>Ludwing Van Beethoven</p>
            </section>
            <section className="presentation">
                <div className="container">
                    <div className="presentation-container">
                        <div className="presentation-item">
                            <h1>Realize</h1>
                            <h1>Negocie</h1>
                            <h1>Compartilhe</h1>
                            <h2>Divulgue o seu trabalho e alcance a oportunidade que tanto almeja!</h2>
                            <h2>Divulgue o seu trabalho e alcance a oportunidade que tanto almeja!</h2>
                            <h2>Divulgue o seu trabalho e alcance a oportunidade que tanto almeja!</h2>
                        </div>
                        <div className="presentation-item">
                            <div className="img-presentation">
                                <h2>Realize</h2>
                            </div>
                            <div className="img-presentation">
                                <h2>Negocie</h2>
                            </div>
                            <div className="img-presentation">
                                <h2>Compartilhe</h2>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section className="player">
                <h1>Compartilhe <span>seu som!</span></h1>
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