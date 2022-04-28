package soungegroup.soungeapi.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {
    @Schema(description = "ID do usuário",
            example = "6")
    private Long id;
    @Schema(description = "Nome do usuário",
            example = "Robson da Silva Junior")
    private String name;
    @Schema(description = "URL da foto do usuário",
            example = "https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
    private String profilePic;
    @Schema(description = "Usuário é ou não é líder de banda")
    private boolean leader;
    @Schema(description = "Quantidade de notificações comuns novas",
            example = "10")
    private int newNotifications;
    @Schema(description = "Quantidade de matches novos",
            example = "10")
    private int newMatches;
}