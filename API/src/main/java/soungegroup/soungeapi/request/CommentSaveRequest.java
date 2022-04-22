package soungegroup.soungeapi.request;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

@Data
public class CommentSaveRequest {
    @NotNull
    private Long userId;
    private String text;
    @URL
    private String mediaUrl;
}
