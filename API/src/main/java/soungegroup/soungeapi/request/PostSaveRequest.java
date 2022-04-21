package soungegroup.soungeapi.request;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.model.Comment;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostSaveRequest {
    @NotNull
    private Long userId;
    private String text;
    @URL
    private String mediaUrl;
    @NotEmpty
    private List<GenreName> genres;
}
