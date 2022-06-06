import { useForm, SubmitHandler } from "react-hook-form";
import { Button, Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";

import "../styles/login.css";

import UserRoute from "../routes/UserRoute";

import IUserLoginRequestDto from "../dto/request/UserLoginRequestDto";

export function Login() {
  const navigate = useNavigate();

  const MySwal = withReactContent(Swal);

  const { register, handleSubmit } = useForm<IUserLoginRequestDto>();

  const onSubmit: SubmitHandler<IUserLoginRequestDto> = (data) => {
    UserRoute.login(data)
      .then((res) => {
        if (res.status == 200) {
          localStorage.setItem("viewerId", `${res.data.id}`);
          navigate(`/profile/${res.data.id}`);
        }
      })
      .catch((err) => {
        console.log(err);
        Swal.fire("erro ao tentar entrar");
      });
  };

  const navigateToHome = () => {
    navigate("/");
  };

  return (
    <div className="page-login">
      <section className="login-container">
        <i
          className="bx bx-right-arrow-alt fechar"
          onClick={navigateToHome}
        ></i>
        <div className="titulo">
          <h1>Entrar</h1>
        </div>
        <div className="corpo">
          <div className="dados">
            <Form onSubmit={handleSubmit(onSubmit)}>
              <Form.Group controlId="formGroupEmail">
                <Form.Control
                  {...register("email")}
                  type="text"
                  placeholder="E-mail"
                  className="campo input-all"
                />
              </Form.Group>
              <Form.Group controlId="formGroupPassword">
                <Form.Control
                  {...register("password")}
                  type="password"
                  placeholder="Senha"
                  className="campo input-all"
                />
              </Form.Group>
              <Button type="submit" className="button2">
                Entrar
              </Button>
            </Form>
          </div>
        </div>
      </section>
    </div>
  );
}
