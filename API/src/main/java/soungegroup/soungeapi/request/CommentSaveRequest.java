package soungegroup.soungeapi.request;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import soungegroup.soungeapi.enums.GenreName;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CommentSaveRequest {
    @NotNull
    private Long userId;
    private String text;
    @URL
    private String mediaUrl;
}
