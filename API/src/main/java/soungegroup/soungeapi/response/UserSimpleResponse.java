package soungegroup.soungeapi.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleResponse {
    @Schema(description = "ID do usuário",
            example = "6")
    private Long id;
    @Schema(description = "Nome do usuário",
            example = "Robson da Silva Junior")
    private String name;
    @Schema(description = "URL da foto do usuário",
            example = "https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
    private String profilePic;
    @ArraySchema(uniqueItems = true, minItems = 1, arraySchema =
    @Schema(description = "Funções desempenhadas pelo usuário",
            oneOf = RoleSimpleResponse.class))
    private List<RoleSimpleResponse> roles;
    @Schema(description = "Usuário é ou não é líder do grupo em que está",
            example = "true")
    private boolean isLeader;
}
