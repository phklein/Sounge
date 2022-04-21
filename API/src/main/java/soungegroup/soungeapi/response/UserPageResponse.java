package soungegroup.soungeapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.enums.Sex;
import soungegroup.soungeapi.enums.SkillLevel;
import soungegroup.soungeapi.enums.State;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPageResponse {
    private Long id;
    private String name;
    private Sex sex;
    private String description;
    private Integer age;
    private State state;
    private String city;
    private boolean leader;
    private SkillLevel skillLevel;
    private String pictureUrl;
    private GroupSimpleResponse group;
    private List<GenreSimpleResponse> likedGenres;
    private List<RoleSimpleResponse> roles;
}
