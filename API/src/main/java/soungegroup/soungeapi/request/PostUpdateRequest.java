package soungegroup.soungeapi.request;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import soungegroup.soungeapi.enums.GenreName;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PostUpdateRequest {
    private String text;
    @URL
    private String mediaUrl;
}
