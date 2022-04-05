package soungegroup.soungeapi.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import soungegroup.soungeapi.enums.State;
import soungegroup.soungeapi.model.relations.UserLikesGenre;
import soungegroup.soungeapi.model.relations.UserLikesPost;
import soungegroup.soungeapi.model.relations.UserLikesUser;
import soungegroup.soungeapi.strategy.UserTypeStrategy;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@Table(name = "tb_user")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements UserTypeStrategy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") private Long id;
    @Column(name = "user_email") private String email;
    @Column(name = "user_password_hash") private String passwordHash;
    @Column(name = "user_name") private String name;
    @Column(name = "user_description") private String description;
    @Column(name = "user_birth_date") private LocalDate birthDate;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_state") private State state;
    @Column(name = "user_city") private String city;
    @Column(name = "user_latitude") private String latitude;
    @Column(name = "user_longitude") private String longitude;

    // One user has many posts
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;

    // One user has many comments
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    // One user has many schedules
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Schedule> schedules;

    // Many users like many genres
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserLikesGenre> likedGenresAssoc;

    // Many users like many posts
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserLikesPost> likedPostsAssoc;

    // Many users like many users
    @OneToMany(mappedBy = "liker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserLikesUser> likedUsersAssoc;

    // Many users are liked by many users
    @OneToMany(mappedBy = "liked", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserLikesUser> usersWhoLikedAssoc;

    // Many users can have one signature
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signature_fk")
    private Signature signature;

    public List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        likedGenresAssoc.forEach(ulg -> genres.add(ulg.getGenre()));
        return genres;
    }

    public List<Post> getLikedPosts() {
        List<Post> posts = new ArrayList<>();
        likedPostsAssoc.forEach(ulp -> posts.add(ulp.getPost()));
        return posts;
    }

    public List<User> getLikedUsers() {
        List<User> users = new ArrayList<>();
        likedUsersAssoc.forEach(ulu -> users.add(ulu.getLiked()));
        return users;
    }

    public List<User> getUsersWhoLiked() {
        List<User> users = new ArrayList<>();
        usersWhoLikedAssoc.forEach(ulu -> users.add(ulu.getLiker()));
        return users;
    }
}
