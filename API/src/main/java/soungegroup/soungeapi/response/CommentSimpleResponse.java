package soungegroup.soungeapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentSimpleResponse {
    private Long id;
    private String text;
    private String mediaUrl;
    private Long hoursPast;
    private UserSimpleResponse user;
}
