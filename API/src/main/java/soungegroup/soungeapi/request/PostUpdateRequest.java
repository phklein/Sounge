package soungegroup.soungeapi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class PostUpdateRequest {
    @Schema(description = "Texto a ser inserido",
            example = "Saudades desse dia!")
    private String text;
    @URL
    @Schema(description = "URL da mídia a ser inserida",
            example = "https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
    private String mediaUrl;
}
