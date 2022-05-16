package soungegroup.soungeapi.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
public class UserSimpleResponse {
    @Schema(description = "ID do usuário",
            example = "6")
    private Long id;
    @Schema(description = "Nome do usuário",
            example = "Robson da Silva Junior")
    private String name;
    @Schema(description = "URL da foto do usuário",
            example = "https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
    private byte[] profilePic;
    @Schema(description = "Usuário é ou não é líder do grupo em que está",
            example = "true")
    private boolean isLeader;

    public UserSimpleResponse(Long id, String name, byte[] profilePic, boolean isLeader) {
        this.id = id;
        this.name = name;
        this.profilePic = profilePic;
        this.isLeader = isLeader;
    }

    public UserSimpleResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }
}
