package soungegroup.soungeapi.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PasswordChangeRequest {
    @Length(min = 8)
    private String oldPassword;
    @Length(min = 8)
    private String newPassword;
}
