package soungegroup.soungeapi.domain.model.relations;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import soungegroup.soungeapi.domain.model.relations.idclass.UserLikesGenreId;
import soungegroup.soungeapi.domain.model.tags.Genre;
import soungegroup.soungeapi.domain.model.users.User;

import javax.persistence.*;

@Entity(name = "UserLikesGenre")
@Table(name = "tb_user_likes_genre")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserLikesGenreId.class)
public class UserLikesGenre {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_fk", referencedColumnName = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "genre_fk", referencedColumnName = "genre_id")
    private Genre genre;
}
