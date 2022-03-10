package soungegroup.soungeapi.domain.model.relations;

import lombok.*;
import soungegroup.soungeapi.domain.model.relations.idclass.UserLikesUserId;
import soungegroup.soungeapi.domain.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "UserLikesUser")
@Table(name = "tb_user_likes_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserLikesUserId.class)
public class UserLikesUser {
    @Id
    @ManyToOne
    @JoinColumn(name = "liker_fk", referencedColumnName = "user_id")
    private User liker;

    @Id
    @ManyToOne
    @JoinColumn(name = "liked_fk", referencedColumnName = "user_id")
    private User liked;

    @Column(name = "like_date_time") private LocalDateTime likeDateTime;
}
