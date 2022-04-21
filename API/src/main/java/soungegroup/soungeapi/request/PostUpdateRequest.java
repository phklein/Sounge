package soungegroup.soungeapi.request;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class PostUpdateRequest {
    private String text;
    @URL
    private String mediaUrl;
}
