package soungegroup.soungeapi.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import soungegroup.soungeapi.enums.State;
import soungegroup.soungeapi.model.Signature;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMatchResponse {
    @Schema(description = "ID do grupo",
            example = "6")
    private Long id;
    @Schema(description = "ID do líder do grupo",
            example = "180")
    private Long leaderId;
    @Schema(description = "Nome do grupo",
            example = "Rocky Hills")
    private String name;
    @Schema(description = "URL da foto do grupo",
            example = "https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
    private String profilePic;
    @Schema(description = "Estado de residência do líder do grupo",
            example = "SP")
    private State leaderState;
    @Schema(description = "Cidade de residência do líder do grupo",
            example = "São Paulo")
    private String leaderCity;
    @Schema(description = "Descrição do grupo",
            example = "Grupo de rock!!!!!")
    private String description;
    @ArraySchema(uniqueItems = true, minItems = 1, arraySchema =
    @Schema(description = "Gêneros musicais relacionados à banda",
            oneOf = GenreSimpleResponse.class))
    private List<GenreSimpleResponse> genres;
    @Schema(description = "Tamanho da banda (quantidade de pessoas)",
            example = "5")
    private Integer size;
    @ArraySchema(uniqueItems = true, minItems = 1, arraySchema =
    @Schema(description = "Funções que a banda já possui",
            oneOf = RoleSimpleResponse.class))
    private List<RoleSimpleResponse> rolesFilled;
    @Schema(description = "Idade da banda",
            example = "23")
    private Integer age;
    @Schema(description = "Distância do líder da banda em Km",
            example = "2.7")
    private Double leaderDistance;
    @Schema(description = "Relevância da banda",
            example = "2.7")
    private Double relevance;
    @Schema(description = "Líder tem ou não tem assinatura")
    private boolean leaderHasSignature;

    @Getter(AccessLevel.NONE)
    private Double leaderLatitude;

    @Getter(AccessLevel.NONE)
    private Double leaderLongitude;

    public GroupMatchResponse(Long id,
                              Long leaderId,
                              String name,
                              String profilePic,
                              State leaderState,
                              String leaderCity,
                              Double leaderLatitude,
                              Double leaderLongitude,
                              String description,
                              Integer size,
                              Signature signature,
                              LocalDate creationDate) {
        this.id = id;
        this.leaderId = leaderId;
        this.name = name;
        this.profilePic = profilePic;
        this.leaderState = leaderState;
        this.leaderCity = leaderCity;
        this.leaderLatitude = leaderLatitude;
        this.leaderLongitude = leaderLongitude;
        this.description = description;
        this.size = size;
        this.leaderHasSignature = signature.isExpired();
        this.age = Period.between(creationDate, LocalDate.now()).getYears();
    }

    public Double latitude() {
        return this.leaderLatitude;
    }

    public Double longitude() {
        return this.leaderLongitude;
    }
}
