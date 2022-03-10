package soungegroup.soungeapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import soungegroup.soungeapi.domain.model.relations.PostHasGenre;
import soungegroup.soungeapi.domain.model.relations.UserLikesPost;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Post")
@Table(name = "tb_post")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id") private Long id;
    @Column(name = "post_text") private String text;
    @Column(name = "post_media_url") private String mediaUrl;
    @Column(name = "post_date_time") private LocalDateTime postDateTime;

    // Many posts belong to one user
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_fk")
    private User user;

    // One post has many comments
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    // Many posts are associated to many genres
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostHasGenre> genresAssoc;

    // Many posts are liked by many users
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserLikesPost> usersWhoLikeAssoc;

    public List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        genresAssoc.forEach(phg -> genres.add(phg.getGenre()));
        return genres;
    }

    public Integer getLikeCount() {
        return usersWhoLikeAssoc.size();
    }
}
