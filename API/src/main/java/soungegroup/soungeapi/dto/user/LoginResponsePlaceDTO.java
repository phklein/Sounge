package soungegroup.soungeapi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import soungegroup.soungeapi.enums.Gender;
import soungegroup.soungeapi.enums.UserType;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoginResponsePlaceDTO extends LoginResponseUserDTOStrategy {
    public UserType getUserType() {
        return UserType.PLACE;
    }
}
