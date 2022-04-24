package soungegroup.soungeapi.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class UserPasswordUpdateRequest {
    @Length(min = 8)
    @NotNull
    private String oldPassword;
    @Length(min = 8)
    @NotNull
    private String newPassword;
}
