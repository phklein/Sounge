package soungegroup.soungeapi.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserProfileUpdateRequest {
    @NotBlank
    private String name;
    private String description;
    private String spotifyId;
}
