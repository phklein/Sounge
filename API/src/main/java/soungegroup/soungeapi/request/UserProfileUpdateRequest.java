package soungegroup.soungeapi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserProfileUpdateRequest {
    @NotBlank
    @Schema(description = "Nome de usuário a ser inserido",
            example = "Renata Ferreira dos Santos")
    private String name;
    @Schema(description = "Descrição a ser inserida",
            example = "Faço parte de uma dupla sertaneja")
    private String description;
    @Schema(description = "ID do spotify a ser inserido",
            example = "exemplo-id-spotify-123")
    private String spotifyID;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpotifyID() {
        return spotifyID;
    }

    public void setSpotifyID(String spotifyID) {
        this.spotifyID = spotifyID;
    }
}
