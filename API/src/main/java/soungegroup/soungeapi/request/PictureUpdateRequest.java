package soungegroup.soungeapi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class PictureUpdateRequest {
    @URL
    @Schema(description = "URL da nova foto",
            example = "https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
    private String url;
}
