import { useNavigate, Link } from 'react-router-dom'

import '../styles/register.css'

import { Button } from '../components/Button'

export function Register() {
    return (
        <>
            <div className="max-width-height">
                <div className="register-container-left">

                    <Button>Cancelar</Button>
                    <Button>Próximo</Button>
                </div>
                <div className="register-container-right">
                    
                </div>
            </div>
        </>
    )
}