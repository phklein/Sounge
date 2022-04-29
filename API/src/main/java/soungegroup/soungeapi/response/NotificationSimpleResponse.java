package soungegroup.soungeapi.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import soungegroup.soungeapi.enums.NotificationType;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.util.Mapper;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSimpleResponse {
    @Schema(description = "ID da notificação",
            example = "4")
    private Long id;
    @Schema(description = "Texto da notificação",
            example = "Fulano deu like em seu post")
    private String text;
    @Schema(description = "Tipo da notificação")
    private NotificationType type;
    @Schema(description = "Horas desde a criação da notificação",
            example = "2")
    private Long hoursPast;
    @Schema(description = "Usuário originário da notificação",
            oneOf = UserSimpleResponse.class)
    private UserSimpleResponse sender;

    public NotificationSimpleResponse(Long id,
                                      String text,
                                      NotificationType type,
                                      LocalDateTime creationDateTime,
                                      User sender) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.hoursPast = Duration.between(creationDateTime, LocalDateTime.now()).toHours();
        this.sender = Mapper.INSTANCE.map(sender, UserSimpleResponse.class);
    }
}
