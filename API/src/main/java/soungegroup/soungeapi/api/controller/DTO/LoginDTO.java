package soungegroup.soungeapi.api.controller.DTO;


import soungegroup.soungeapi.domain.model.users.User;

public class LoginDTO {
    private String email;
    private String senha;
    private User user;


    public User hasUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public void setSenha(String senha) {
        this.senha = senha;
    }


    public String hasSenha() {
              return  this.senha;
    }
}
