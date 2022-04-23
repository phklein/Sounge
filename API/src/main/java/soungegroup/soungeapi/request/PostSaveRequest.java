package soungegroup.soungeapi.request;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import soungegroup.soungeapi.enums.GenreName;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class PostSaveRequest {
    @NotNull
    @Positive
    private Long userId;
    private String text;
    @URL
    private String mediaUrl;
    @NotEmpty
    private List<GenreName> genres;
}
