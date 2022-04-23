package soungegroup.soungeapi.request;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateGroupPageRequest {
    @URL
    private String pictureUrl;
    @NotBlank
    private String name;
    private String description;
}
