import { useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";


export function Interaction() {
    return (
        <div className="interaction">
            <div className="interaction-barr">
            
                <div className="interaction-icon">
                    <i id="coracao"className='bx bxs-heart'></i>
                    <h3>Curtir</h3>
                </div>
                <div className="interaction-icon">
                    <i className='bx bxs-conversation'></i>
                    <h3>Comentar</h3>
                </div>
                <div className="interaction-icon">
                    <i className='bx bxs-share'></i>
                    <h3>Compartilhar</h3>
                </div>
                
            </div>

        </div>
    )
}
