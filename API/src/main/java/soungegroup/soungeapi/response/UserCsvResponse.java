package soungegroup.soungeapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.enums.Sex;
import soungegroup.soungeapi.enums.State;
<<<<<<< HEAD:API/src/main/java/soungegroup/soungeapi/response/LoginResponse.java
import soungegroup.soungeapi.enums.UserType;
import soungegroup.soungeapi.strategy.UserTypeStrategy;
=======
>>>>>>> develop:API/src/main/java/soungegroup/soungeapi/response/UserCsvResponse.java

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCsvResponse {
    private Long id;
    private String name;
    private Sex sex;
    private String description;
    private LocalDate birthDate;
    private State state;
    private String city;
<<<<<<< HEAD:API/src/main/java/soungegroup/soungeapi/response/LoginResponse.java
    private String latitude;
    private String longitude;
    private UserType type;
=======
    private boolean leader;
>>>>>>> develop:API/src/main/java/soungegroup/soungeapi/response/UserCsvResponse.java
}
