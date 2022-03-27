package soungegroup.soungeapi.dto.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import soungegroup.soungeapi.enums.Gender;
import soungegroup.soungeapi.enums.UserType;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class LoginResponseArtist extends LoginResponse {
    private Gender gender;

    @Override
    public UserType getUserType() {
        return UserType.ARTIST;
    }
}
