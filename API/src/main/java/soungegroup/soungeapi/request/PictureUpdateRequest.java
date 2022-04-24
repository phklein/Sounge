package soungegroup.soungeapi.request;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class PictureUpdateRequest {
    @URL
    private String url;
}
