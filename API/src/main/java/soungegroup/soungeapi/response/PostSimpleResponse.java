package soungegroup.soungeapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.model.Comment;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSimpleResponse {
    private Long id;
    private String text;
    private String mediaUrl;
    private Long hoursPast;
    private UserSimpleResponse user;
    private Integer likeCount;
    private Integer commentCount;
}
