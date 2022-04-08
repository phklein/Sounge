import { useNavigate, Link } from 'react-router-dom'

import '../styles/register.css'

import { Button } from '../components/Button'

export function Register() {
    return (
        <>
            <div className="max-width-height">
                <div className="register-container-left">
                    <form action="POST">
                        <div className="register-form">
                            <h2>Crie sua conta</h2>
                            <input type="text" className="input-all" name="nome" id="nome" placeholder="Nome" />
                            <div className="container-input-hlf">
                                <input type="email" className="input-hlf" name="email" id="email" placeholder="E-mail" />
                                <input type="number" className="input-hlf" name="cpf" id="cpf" placeholder="CPF" />
                            </div>
                            <div className="container-input-hlf">
                                <input type="text" className="input-hlf" name="estado" id="estado" placeholder="Estado" maxLength={2} />
                                <input type="text" className="input-hlf" name="cidade" id="cidade" placeholder="Cidade" />
                            </div>
                            <input type="password" className="input-all" name="senha" id="senha" placeholder="Senha" />
                            <input type="password" className="input-all" name="repetirSenha" id="repetirSenha" placeholder="RepetirSenha" />
                        </div>
                        <Button>Cancelar</Button>
                        <Button>Próximo</Button>
                    </form>
                </div>
                <div className="register-container-right">
                    <img src="https://s3-alpha-sig.figma.com/img/e68c/cd7e/cf67a34866a0ad05813a407693875fe9?Expires=1650240000&Signature=E-O1wX8pmHh~GNUyuTcBbpX6HVwjrSN7CJcCe7AUNxIP818-y-CtQallqQj7M-CdCB6ZIXU4wnBJeO0FOOS9GytnaOwPd5abRrVWrvqfzr-hqBCR26W-UZ8Ha09dbjy2ZopOBOCSbA9zY1Xk5s~zYdODXBVg9hy-l4ZV9oKmGeFMiEZL5Xz8MmoInwEstgeZC-8Qa8uh9NACf-t-mnpZFsTlVZ1ZP6ckb1MONrBp16iW-616K6Msl0pw12YTlZ9LeQjK6s8rbCPw9K9H3ffDIYWQCwXBrr8~7b7XZx9s6BxXf9Gl8JL0JZc5a45O2npl9vN3xZSkvHWUbJgQ4M~-pw__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA" alt="" />
                    <h1>Crie sua conta<br />aqui agora!</h1>
                    <p>Lorem ipsum dolor sit amet, consectetur incididunt ut labore et dolore magna aliqua.</p>
                </div>
            </div>
        </>
    )
}