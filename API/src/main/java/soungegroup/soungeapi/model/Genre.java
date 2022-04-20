package soungegroup.soungeapi.model;

import lombok.*;
import soungegroup.soungeapi.enums.GenreName;

import javax.persistence.*;
import java.util.ArrayList;
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
    @ManyToMany(mappedBy = "likedGenres", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> usersWhoLike;

    // Many genres are associated to many posts
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;

    // Many genres are associated to many groups
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Group> groups;
}
