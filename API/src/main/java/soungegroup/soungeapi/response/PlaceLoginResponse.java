package soungegroup.soungeapi.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import soungegroup.soungeapi.enums.UserType;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PlaceLoginResponse extends LoginResponse {
    @Override
    public UserType getUserType() {
        return UserType.PLACE;
    }
}
