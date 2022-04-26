package soungegroup.soungeapi.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.enums.SkillLevel;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMatchResponse {
    @Schema(description = "ID do usuário",
            example = "6")
    private Long id;
    @Schema(description = "Nome do usuário",
            example = "Robson da Silva Junior")
    private String name;
    @Schema(description = "URL da foto do usuário",
            example = "https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
    private String profilePic;
    @Schema(description = "Usuário é ou não é líder do grupo em que está",
            example = "true")
    private boolean isLeader;
    @Schema(description = "ID do spotify do usuário",
            example = "exemplo-id-spotify-123")
    private String spotifyID;
    @Schema(description = "Descrição do usuário",
            example = "Toco guitarra desde pequeno, sou apaixonado por rock")
    private String description;
    @Schema(description = "Usuário está ou não está online",
            example = "false")
    private Boolean isOnline;
    @Schema(description = "Nível de experiência do usuário com música",
            example = "ADVANCED")
    private SkillLevel skillLevel;
    @Schema(description = "Nível de experiência do usuário com música",
            oneOf = GroupSimpleResponse.class)
    private GroupSimpleResponse group;
    @ArraySchema(uniqueItems = true, minItems = 1, arraySchema =
    @Schema(description = "Gêneros musicais relacionados ao usuário",
            oneOf = GenreSimpleResponse.class))
    private List<GenreSimpleResponse> likedGenres;
    @ArraySchema(uniqueItems = true, minItems = 1, arraySchema =
    @Schema(description = "Funções desempenhadas pelo usuário",
            oneOf = RoleSimpleResponse.class))
    private List<RoleSimpleResponse> roles;
    @Schema(description = "Idade do usuário",
            example = "23")
    private Integer age;

    public UserMatchResponse(Long id,
                             String name,
                             String profilePic,
                             boolean isLeader,
                             String spotifyID,
                             String description,
                             SkillLevel skillLevel,
                             LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.profilePic = profilePic;
        this.isLeader = isLeader;
        this.spotifyID = spotifyID;
        this.description = description;
        this.skillLevel = skillLevel;
        this.age = Period.between(birthDate, LocalDate.now()).getYears();
    }
}
