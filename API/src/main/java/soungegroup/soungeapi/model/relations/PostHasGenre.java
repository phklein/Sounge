package soungegroup.soungeapi.model.relations;

import lombok.*;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.Post;
import soungegroup.soungeapi.model.relations.idclass.PostHasGenreId;

import javax.persistence.*;

@Entity(name = "PostHasGenre")
@Table(name = "tb_post_has_genre")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PostHasGenreId.class)
public class PostHasGenre {
    @Id
    @ManyToOne
    @JoinColumn(name = "post_fk", referencedColumnName = "post_id")
    private Post post;

    @Id
    @ManyToOne
    @JoinColumn(name = "genre_fk", referencedColumnName = "genre_id")
    private Genre genre;
}
