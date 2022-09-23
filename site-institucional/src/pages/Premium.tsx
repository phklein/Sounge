import { Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

import { NavBar } from "../components/Navbar";

import '../styles/premium.css'

export function Premium() {
    return (
        <div className="page-premium">
            <NavBar />
            <section className="premium-container">
                <div className="titulo-premium">
                    <h2>Seja Premium!</h2>
                </div>

                <div className="premium-banner">
                    <div className="text-premium">
                        <h1>Faça o que você, sua banda ou seu estabelecimento ganhe mais relevância
                            e engajamento e se torne viral! </h1>
                        <Button className="button"> Saber mais </Button>
                    </div>
                    <div className="imagem-premium"> </div>
                </div>

            </section>

            <section className="premium-planos">
                <div className="titulo-premium">
                    <h2>Escolha seu plano</h2>
                </div>

                <div className="nossos-planos">
                    <div className="tipos-planos">
                        <div className="planos">
                            <h1 className="nome-plano">Plano Bronze</h1>
                            <p>plano mensal</p>
                            <p>R$</p>
                            <h1 className="reais">25</h1>

                            <div className="botao">
                                <Button className="button-premium"> Selecione </Button>
                                <img src=""></img>
                            </div>
                        </div>
                    </div>
                    <div className="tipos-planos">
                        <div className="planos">
                            <h1 className="nome-plano">Plano Ouro</h1>
                            <p>plano mensal</p>
                            <p>R$</p>
                            <h1 className="reais">150</h1>

                            <div className="botao">
                                <Button className="button-premium"> Selecione </Button>
                            </div>
                        </div>
                    </div>
                    <div className="tipos-planos">
                        <div className="planos">
                            <h1 className="nome-plano">Plano Prata</h1>
                            <p>plano mensal</p>
                            <p>R$</p>
                            <h1 className="reais">100</h1>

                            <div className="botao">
                                <Button className="button-premium"> Selecione </Button>
                            </div>
                        </div>
                    </div>
                </div>

            </section>
        </div>

    )
}