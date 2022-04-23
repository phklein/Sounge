package soungegroup.soungeapi.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import soungegroup.soungeapi.enums.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
public class UserSaveRequest {
    @Email
    @NotBlank
    private String email;
    @Length(min = 8)
    @NotNull
    private String password;
    @NotBlank
    private String name;
    @NotNull
    private Sex sex;
    private String description;
    @Past
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private State state;
    @NotBlank
    private String city;
    @NotEmpty
    private List<GenreName> likedGenres;
    @NotEmpty
    private List<RoleName> roles;
    private SkillLevel skillLevel;
}
