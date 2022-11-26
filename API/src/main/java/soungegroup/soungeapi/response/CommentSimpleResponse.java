package soungegroup.soungeapi.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.util.Mapper;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentSimpleResponse {
    @Schema(description = "ID do comentário",
            example = "7")
    private Long id;
    @Schema(description = "Texto do comentário",
            example = "Gostei dessa ideia")
    private String text;
    @Schema(description = "URL da mídia do comentário",
            example = "https://www.google.com.br/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")
    private String mediaUrl;
    @Schema(description = "Horas desde a postagem do comentário",
            example = "17")
    private Long hoursPast;
    @Schema(description = "Usuário dono do comentário",
            oneOf = UserSimpleResponse.class)
    private UserSimpleResponse user;
    @Schema(description = "Contagem de likes do comentário",
            example = "1523")
    private Integer likeCount;
    @Schema(description = "Usuário deu ou não deu like neste comentário")
    private boolean hasLiked;

    public CommentSimpleResponse(Long id,
                                 String text,
                                 String mediaUrl,
                                 LocalDateTime postDateTime,
                                 User user,
                                 Integer likeCount) {
        this.id = id;
        this.text = text;
        this.mediaUrl = mediaUrl;
        this.hoursPast = Duration.between(postDateTime, LocalDateTime.now()).toHours();
        this.user = Mapper.INSTANCE.map(user, UserSimpleResponse.class);
        this.likeCount = likeCount;
    }
}
