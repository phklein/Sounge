import { useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";

export function Comment() {
    return (
        <div className="comment-bar">
            <div className="comment-usuario">
                <h3 className="comment-usuarioName">Bruna Yumi Sato</h3>
            </div>
            <input className="comment" type="text" />
        </div>
    )
}