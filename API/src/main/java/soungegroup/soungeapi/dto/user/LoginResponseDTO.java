package soungegroup.soungeapi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.enums.State;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private Long id;
    private String email;
    private String name;
    private LocalDate birthDate;
    private State state;
    private String city;
    private String latitude;
    private String longitude;
}
