package soungegroup.soungeapi.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import soungegroup.soungeapi.enums.SkillLevel;
import soungegroup.soungeapi.enums.State;
import soungegroup.soungeapi.model.Signature;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Schema(description = "Estado de residência do usuário",
            example = "SP")
    private State state;
    @Schema(description = "Cidade de residência do usuário",
            example = "São Paulo")
    private String city;
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
    @Schema(description = "Grupo do usuário",
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
    @Schema(description = "Distância do usuário em Km",
            example = "2.7")
    private Double distance;
    @Schema(description = "Relevância do usuário",
            example = "2.7")
    private Double relevance;
    @Schema(description = "Usuário tem ou não tem assinatura")
    private boolean hasSignature;

    @Getter(AccessLevel.NONE)
    private Double latitude;

    @Getter(AccessLevel.NONE)
    private Double longitude;

    public UserMatchResponse(Long id,
                             String name,
                             String profilePic,
                             boolean isLeader,
                             State state,
                             String city,
                             Double latitude,
                             Double longitude,
                             String spotifyID,
                             String description,
                             Signature signature,
                             SkillLevel skillLevel,
                             LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.profilePic = profilePic;
        this.isLeader = isLeader;
        this.state = state;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.spotifyID = spotifyID;
        this.description = description;
        this.hasSignature = signature.getExpiryDateTime().isAfter(LocalDateTime.now());
        this.skillLevel = skillLevel;
        this.age = Period.between(birthDate, LocalDate.now()).getYears();
    }

    public Double latitude() {
        return this.latitude;
    }

    public Double longitude() {
        return this.longitude;
    }
}
