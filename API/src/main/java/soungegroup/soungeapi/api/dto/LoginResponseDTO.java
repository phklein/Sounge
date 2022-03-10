package soungegroup.soungeapi.api.dto;

import lombok.Builder;
import lombok.Data;
import soungegroup.soungeapi.enums.State;

import java.time.LocalDate;

@Data
@Builder
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
