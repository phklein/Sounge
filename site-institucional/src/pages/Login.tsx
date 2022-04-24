import { Button, Form } from 'react-bootstrap'
import { useNavigate, Link } from 'react-router-dom'

import '../styles/login.css'

export function Login() {
    return (
        <div className="page-login">
            <section className="login-container">
                <i className='bx bx-x fechar'></i>
                <div className="titulo">
                    <h1>Entrar</h1>
                </div>
                <div className="corpo">

                    <div className="dados">
                        <Form>
                            <Form.Group controlId='formGroupEmail'>
                                <Form.Control type="text" placeholder="E-mail" className="campo" />
                            </Form.Group>
                            <Form.Group controlId='formGroupSenha'>
                                <Form.Control type="password" placeholder="Senha" className="campo" />
                            </Form.Group>
                            <Form.Group>
                                <Button className="button2"> Entrar </Button>
                            </Form.Group>
                        </Form>

                    </div>

                </div>
            </section>
        </div>
    )
}