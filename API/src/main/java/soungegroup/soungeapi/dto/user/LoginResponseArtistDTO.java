package soungegroup.soungeapi.dto.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import soungegroup.soungeapi.enums.Gender;
import soungegroup.soungeapi.enums.UserType;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoginResponseArtistDTO extends LoginResponseUserDTOStrategy {
    private Gender gender;

    public UserType getUserType() {
        return UserType.ARTIST;
    }
}
