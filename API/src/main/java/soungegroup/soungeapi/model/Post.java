package soungegroup.soungeapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Post")
@Table(name = "tb_post")
@Getter @Setter
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

    // Many users like many genres
    @ManyToMany
    @JoinTable(name = "tb_post_has_genre",
            joinColumns = @JoinColumn(name = "post_fk"),
            inverseJoinColumns = @JoinColumn(name = "genre_fk"))
    private List<Genre> genres;

    // Many posts are liked by many users
    @ManyToMany(mappedBy = "likedPosts", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> users;
}
