package soungegroup.soungeapi.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.util.Mapper;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostSimpleResponse {
    @Schema(description = "ID do post",
            example = "7")
    private Long id;
    @Schema(description = "Texto do post",
            example = "Gostei dessa ideia")
    private String text;
    @Schema(description = "URL da mídia do post",
            example = "https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
    private String mediaUrl;
    @Schema(description = "Horas desde a postagem do post",
            example = "17")
    private Long hoursPast;
    @Schema(description = "Usuário dono do post",
            oneOf = UserSimpleResponse.class)
    private UserSimpleResponse user;

    @Schema(description = "Banda dona do post",
            oneOf = UserSimpleResponse.class)
    private GroupSimpleResponse group;
    @Schema(description = "Contagem de likes do post",
            example = "1523")
    private Integer likeCount;
    @Schema(description = "Contagem de comentários do post",
            example = "128")
    private Integer commentCount;
    @Schema(description = "Usuário deu ou não deu like neste post")
    private boolean hasLiked;

    public PostSimpleResponse(Long id,
                              String text,
                              String mediaUrl,
                              LocalDateTime postDateTime,
                              User user,
                              Group group,
                              Integer likeCount,
                              Integer commentCount) {
        this.id = id;
        this.text = text;
        this.mediaUrl = mediaUrl;
        this.hoursPast = Duration.between(postDateTime, LocalDateTime.now()).toHours();
        this.user = Mapper.INSTANCE.map(user, UserSimpleResponse.class);
        this.group = Mapper.INSTANCE.map(group, GroupSimpleResponse.class);
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }
}
