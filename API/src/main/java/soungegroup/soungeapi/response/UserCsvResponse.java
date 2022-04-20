package soungegroup.soungeapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.enums.Sex;
import soungegroup.soungeapi.enums.State;

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
    private boolean leader;
}
