package soungegroup.soungeapi.dto.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import soungegroup.soungeapi.enums.UserType;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class LoginResponseGroupDTO extends LoginResponseUserDTO {
    @Override
    public UserType getUserType() {
        return UserType.GROUP;
    }
}
