package soungegroup.soungeapi.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.enums.GenreName;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreSimpleResponse {
    @Schema(description = "ID do gênero musical",
            example = "4")
    private Long id;
    @Schema(description = "Nome do gênero musical",
            example = "SERTANEJO")
    private GenreName name;
}
