package soungegroup.soungeapi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class CommentSaveRequest {
    @NotNull
    @Positive
    @Schema(description = "ID do usuário dono do comentário",
            example = "7")
    private Long userId;
    @Schema(description = "Texto do comentário",
            example = "Gostei dessa ideia")
    private String text;
    @URL
    @Schema(description = "URL da mídia do comentário",
            example = "https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
    private String mediaUrl;
}
