package soungegroup.soungeapi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import soungegroup.soungeapi.enums.State;
import soungegroup.soungeapi.enums.UserType;

import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class LoginResponseUserDTOStrategy {
    private Long id;
    private String email;
    private String name;
    private LocalDate birthDate;
    private State state;
    private String city;
    private String latitude;
    private String longitude;
    public abstract UserType getUserType();
}
