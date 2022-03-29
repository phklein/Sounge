package soungegroup.soungeapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import soungegroup.soungeapi.enums.State;
import soungegroup.soungeapi.strategy.UserTypeStrategy;

import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class LoginResponse implements UserTypeStrategy {
    private Long id;
    private String email;
    private String name;
    private LocalDate birthDate;
    private State state;
    private String city;
    private String latitude;
    private String longitude;
}
