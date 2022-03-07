import { useNavigate, Link } from 'react-router-dom'

export function Footer(props: any) {
    return (
        <footer>
            <div className="container footer-container">
                <div className="footer-item">
                    <img src="https://s3-alpha-sig.figma.com/img/4604/9d10/9f88a9e6d39e19b02a6a98ae0b850350?Expires=1647820800&Signature=bDQN2QTyFmIbqChE2PgfRAJU3zlYB49fks18mND~cRWDINzw0nEgmBEuKNIjnc7-ACoGGelm1EGX8ueTHxwvXjY-sWlJZHiS6eMGOD9OgJjDjNlKULmIyZrzy5wde-YxuJJZnzFYHi2U6Yq5GGVPIppd7JGFMa~2jZVHHp4Z2EX8VirOB4lWQifUoBZ6MwXRh0aB4XO4f6qC46l2JKbXakRSUUWUdMZZPA2mCVcKuzquhXgAXKj1rHVb8Cnd39CuQs-IG9vZa4dQvamuD5wLNbHpE~yJnSv5QfrK-TUu52h8SVTYnfZs-o4T0FumAyrO8zN8FmLT4zfA6pakAC29cw__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA" alt="" />
                    <h4>Sounge 2022.</h4>
                    <p>Todos direitos reservados.</p>
                </div>
                <div className="footer-item">
                    <h3>Siga-nos</h3>
                    <div className="icons-list">
                        <a href=""><div className="icon-social"><i className='bx bxl-facebook'></i></div></a>
                        <a href=""><div className="icon-social"><i className='bx bxl-twitter'></i></div></a>
                        <a href=""><div className="icon-social"><i className='bx bxl-youtube'></i></div></a>
                        <a href=""><div className="icon-social"><i className='bx bxl-instagram-alt'></i></div></a>
                        <a href=""><div className="icon-social"><i className='bx bxl-linkedin'></i></div></a>
                    </div>
                    <h4><i className='bx bxl-whatsapp' ></i> +55 (11) 91234-5678</h4>
                    <h4><i className='bx bxs-phone'></i> +55 (11) 91234-5678</h4>
                </div>
                <div className="footer-item">
                    <nav className="links-footer">
                        <div className="link-item-footer">
                            <h3>A Sounge</h3>
                            <p>Feed</p>
                            <p>Perfils</p>
                            <p>Cadastro</p>
                            <p>Match</p>
                            <p>Chat</p>
                            <p>Impulsos</p>
                            <p>Local</p>
                        </div>
                        <div className="link-item-footer">
                            <h3>Mídia</h3>
                            <p>Notícias</p>
                            <p>Imprensa</p>
                            <p>Vídeos</p>
                            <p>Fotos</p>
                        </div>
                    </nav>
                </div>
            </div>
        </footer>
    )
}