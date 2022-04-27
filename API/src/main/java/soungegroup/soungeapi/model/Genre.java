package soungegroup.soungeapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import soungegroup.soungeapi.enums.GenreName;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Genre")
@Table(name = "tb_genre")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id") private Long id;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "genre_name") private GenreName name;

    // Many genres are liked by many users
    @ManyToMany(mappedBy = "likedGenres", fetch = FetchType.LAZY)
    private List<User> usersWhoLike;

    // Many genres are associated to many posts
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private List<Post> posts;

    // Many genres are associated to many groups
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private List<Group> groups;
}
