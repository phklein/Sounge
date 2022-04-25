package soungegroup.soungeapi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserLoginRequest {
    @Email
    @NotBlank
    @Schema(description = "Email do usuário",
            example = "robson@gmail.com")
    private String email;
    @Length(min = 8)
    @NotNull
    @Schema(description = "Senha do usuário",
            minLength = 8,
            example = "12345678")
    private String password;
}
