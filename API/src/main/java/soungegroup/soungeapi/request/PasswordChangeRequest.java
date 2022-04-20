package soungegroup.soungeapi.request;

import lombok.Data;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.enums.Sex;
import soungegroup.soungeapi.enums.State;

import java.time.LocalDate;
import java.util.List;

@Data
public class PasswordChangeRequest {
    private String oldPassword;
    private String newPassword;
}
