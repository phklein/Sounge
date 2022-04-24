import { NavBar } from "../../components/Navbar";
import "../../styles/profile.css";
import { BtnSintonizar } from "../../components/BtnSintonizar";
import { PageIntroduction } from "../../components/PageIntroduction";
import { PostPage } from "../../components/PostPage";

export function Profile() {
  return (
    <>
      <NavBar />
      <div className="background-profile">
        <div className="profile-container">
          <div className="profile-cap"></div>
          <div className="profile-details">
            <img className="profile-picture"></img>
            <h2 className="profile-user-name">Usuário Nome</h2>
            <h4 className="typography">Descrição do usuário</h4>
          </div>

          <div className="father-menu">
            <div className="menu-profile">
              <h5 className="typography">Feed</h5>
              <h5 className="typography">Sobre</h5>
              <h5 className="typography">Vídeos</h5>
              <h5 className="typography">Playlist</h5>
              <h5 className="typography">Mais</h5>
              <i className="bx bxs-down-arrow seta-baixo "></i>
            </div>
            <div className="btn-space">
              <BtnSintonizar />
            </div>
          </div>
        </div>
      </div>

      <div>
        <div className="profile-container">
          <div style={{ display: "flex" }}>
            <PageIntroduction />
            <div style={{ display: "flex", flexDirection: "column" }}>
              <PostPage />
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
