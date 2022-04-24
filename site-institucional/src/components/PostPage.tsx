import "../styles/postpage.css";

export function PostPage() {
  return (
    <>
      <div className="background-post">
        <div className="profile-details-post">
          <img className="profile-picture-post"></img>
          <p className="profile-user-name-post">Usuário Nome</p>
        </div>

        <div className="background-post-wrapper">
          Um novo álbum em lançamento! Mal posso esperar!
        </div>
      </div>
    </>
  );
}
