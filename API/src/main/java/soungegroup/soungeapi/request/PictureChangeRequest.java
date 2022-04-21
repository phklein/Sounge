package soungegroup.soungeapi.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Data
public class PictureChangeRequest {
    @URL
    private String url;
}
