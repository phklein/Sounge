package soungegroup.soungeapi.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.enums.RoleName;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleSimpleResponse {
    @Schema(description = "ID da função",
            example = "4")
    private Long id;
    @Schema(description = "Nome da função",
            example = "VOCALIST")
    private RoleName name;
}
