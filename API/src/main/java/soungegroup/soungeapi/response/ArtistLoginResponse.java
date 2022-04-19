package soungegroup.soungeapi.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import soungegroup.soungeapi.enums.Gender;
import soungegroup.soungeapi.enums.UserType;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ArtistLoginResponse extends LoginResponse {
    private Gender gender;

    @Override
    public UserType getUserType() {
        return UserType.ARTIST;
    }
}
