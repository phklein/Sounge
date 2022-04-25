package soungegroup.soungeapi.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSimpleResponse {
    @Schema(description = "ID do post",
            example = "7")
    private Long id;
    @Schema(description = "Texto do post",
            example = "Gostei dessa ideia")
    private String text;
    @Schema(description = "URL da mídia do post",
            example = "https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
    private String mediaUrl;
    @Schema(description = "Horas desde a postagem do post",
            example = "17")
    private Long hoursPast;
    @Schema(description = "Usuário dono do post",
            oneOf = UserSimpleResponse.class)
    private UserSimpleResponse user;
    @Schema(description = "Contagem de likes do post",
            example = "1523")
    private Integer likeCount;
    @Schema(description = "Contagem de comentários do post",
            example = "128")
    private Integer commentCount;
}
