package soungegroup.soungeapi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class UserPasswordUpdateRequest {
    @Length(min = 8)
    @NotNull
    @Schema(description = "Senha atual, para autenticação",
            minLength = 8,
            example = "12345678")
    private String oldPassword;
    @Length(min = 8)
    @NotNull
    @Schema(description = "Senha nova",
            minLength = 8,
            example = "87654321")
    private String newPassword;
}
