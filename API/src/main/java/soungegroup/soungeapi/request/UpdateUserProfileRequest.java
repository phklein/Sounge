package soungegroup.soungeapi.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

@Data
public class UpdateUserProfileRequest {
    @Length(min = 8)
    @NotNull
    private String password;
    private String description;
    private String spotifyId;
    @URL
    private String profilePic;
}
