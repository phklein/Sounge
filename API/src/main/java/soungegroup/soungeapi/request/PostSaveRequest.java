package soungegroup.soungeapi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Positive;

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
    @Schema(description = "URL da mídia do post",
            example = "https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
    private String mediaUrl;
}
