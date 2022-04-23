package soungegroup.soungeapi.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserLoginRequest {
    @Email
    @NotBlank
    private String email;
    @Length(min = 8)
    @NotNull
    private String password;
}
