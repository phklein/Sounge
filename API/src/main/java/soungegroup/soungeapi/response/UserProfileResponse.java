package soungegroup.soungeapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.enums.SkillLevel;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private UserSimpleResponse user;
    private List<PostSimpleResponse> postList;
    private String profilePic;
    private String spotifyID;
    private String description;
    private Boolean isOnline;
    private SkillLevel skillLevel;
    private GroupSimpleResponse group;
    private List<GenreSimpleResponse> likedGenres;
    private List<RoleSimpleResponse> roles;
    private Integer age;
}
