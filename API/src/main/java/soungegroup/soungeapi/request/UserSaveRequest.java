package soungegroup.soungeapi.request;

import lombok.Data;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.enums.Sex;
import soungegroup.soungeapi.enums.State;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserSaveRequest {
    private String email;
    private String password;
    private String name;
    private Sex sex;
    private String description;
    private LocalDate birthDate;
    private State state;
    private String city;
    private List<GenreName> likedGenres;
    private List<RoleName> roles;
}
