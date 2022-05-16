package soungegroup.soungeapi.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupSimpleResponse {
    @Schema(description = "ID do grupo",
            example = "3")
    private Long id;
    @Schema(description = "Nome do grupo",
            example = "Turma do Sertanejo")
    private String name;
    @Schema(description = "URL da foto do grupo",
            example = "https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
    private byte[] profilePic;
}
