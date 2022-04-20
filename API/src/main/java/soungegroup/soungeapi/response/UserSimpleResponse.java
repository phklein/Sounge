package soungegroup.soungeapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.enums.Sex;
import soungegroup.soungeapi.enums.SkillLevel;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleResponse {
    private Long id;
    private String name;
    private Sex sex;
    private List<GenreSimpleResponse> likedGenres;
    private List<RoleSimpleResponse> roles;
    private boolean leader;
    private SkillLevel skillLevel;
}
