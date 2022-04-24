package soungegroup.soungeapi.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GroupPageUpdateRequest {
    @NotBlank
    private String name;
    private String description;
}
