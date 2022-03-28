package soungegroup.soungeapi.model;

import lombok.*;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.model.relations.PostHasGenre;
import soungegroup.soungeapi.model.relations.UserLikesGenre;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Genre")
@Table(name = "tb_genre")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id") private Long id;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "genre_name") private GenreName name;

    // Many genres are associated to many posts
    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostHasGenre> postsAssoc;

    // Many genres are liked by many users
    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserLikesGenre> usersWhoLikeAssoc;

    public List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();
        postsAssoc.forEach(phg -> posts.add(phg.getPost()));
        return posts;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        usersWhoLikeAssoc.forEach(ulg -> users.add(ulg.getUser()));
        return users;
    }
}
