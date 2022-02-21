package soungegroup.soungeapi.domain.model.relations;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import soungegroup.soungeapi.domain.model.feed.Post;
import soungegroup.soungeapi.domain.model.relations.idclass.UserLikesPostId;
import soungegroup.soungeapi.domain.model.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "UserLikesPost")
@Table(name = "tb_user_likes_post")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserLikesPostId.class)
public class UserLikesPost {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_fk", referencedColumnName = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "post_fk", referencedColumnName = "post_id")
    private Post post;

    @Column(name = "like_date_time") private LocalDateTime likeDateTime;
}
