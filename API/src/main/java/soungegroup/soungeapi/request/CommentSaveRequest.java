package soungegroup.soungeapi.request;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class CommentSaveRequest {
    @NotNull
    @Positive
    private Long userId;
    private String text;
    @URL
    private String mediaUrl;
}
