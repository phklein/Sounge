package soungegroup.soungeapi.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import soungegroup.soungeapi.enums.GenreName;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class PostSaveRequest {

    @Positive
    @Schema(description = "ID do usuário dono do post",
            example = "6")
    private Long userId;
    @Positive
    @Schema(description = "ID da banda dona do post",
            example = "6")
    private Long groupId;
    @Schema(description = "Texto do post",
            example = "O que acham dessa banda?")
    private String text;
    @URL
    @Schema(description = "URL da mídia do post",
            example = "https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
    private String mediaUrl;
    @NotEmpty
    @ArraySchema(uniqueItems = true, minItems = 1, arraySchema =
    @Schema(description = "Gêneros musicais relacionados ao post",
            example = "[\"METAL\", \"ROCK\"]"))
    private List<GenreName> genres;
}
