package soungegroup.soungeapi.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupPageResponse {
    @Schema(description = "ID do grupo",
            example = "3")
    private Long id;
    @Schema(description = "Nome do grupo",
            example = "Turma do Sertanejo")
    private String name;
    @Schema(description = "Descrição do grupo",
            example = "Grupo sertanejo de São Paulo")
    private String description;
    @Schema(description = "Idade do grupo",
            example = "1")
    private Integer age;
    @ArraySchema(uniqueItems = true, minItems = 1, arraySchema =
    @Schema(description = "Gêneros musicais relacionados ao grupo",
            oneOf = GenreSimpleResponse.class))
    private List<GenreSimpleResponse> genres;
    @ArraySchema(uniqueItems = true, arraySchema =
    @Schema(description = "Membros do grupo grupo",
            oneOf = UserSimpleResponse.class))
    private List<UserSimpleResponse> users;
    @Schema(description = "URL da foto do grupo",
            example = "https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
    private String pictureUrl;
    @Schema(description = "URL da foto do banner",
            example = "https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
    private String banner;
    @ArraySchema(uniqueItems = true, arraySchema =
    @Schema(description = "Posts feitos pelos usuários da banda",
            oneOf = PostSimpleResponse.class))
    private Page<PostSimpleResponse> postList;

    public GroupPageResponse(Long id,
                             String name,
                             String description,
                             LocalDate creationDate,
                             String pictureUrl,
                             String banner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.age = Period.between(creationDate, LocalDate.now()).getYears();
        this.pictureUrl = pictureUrl;
        this.banner = banner;
    }
}
