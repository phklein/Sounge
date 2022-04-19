package soungegroup.soungeapi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import soungegroup.soungeapi.enums.State;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class UserSaveRequest {
    private String email;
    private String password;
    private String name;
    private String description;
    private LocalDate birthDate;
    private State state;
    private String city;
    private List<GenreSaveRequest> likedGenres;
}