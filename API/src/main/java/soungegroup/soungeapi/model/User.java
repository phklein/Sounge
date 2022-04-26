package soungegroup.soungeapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import soungegroup.soungeapi.enums.Sex;
import soungegroup.soungeapi.enums.SkillLevel;
import soungegroup.soungeapi.enums.State;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "User")
@Table(name = "tb_user")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") private Long id;
    @Column(name = "user_email") private String email;
    @Column(name = "user_password") private String password;
    @Column(name = "user_name") private String name;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_sex") private Sex sex;
    @Column(name = "user_description") private String description;
    @Column(name = "user_birth_date") private LocalDate birthDate;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_state") private State state;
    @Column(name = "user_city") private String city;
    @Column(name = "user_latitude") private String latitude;
    @Column(name = "user_longitude") private String longitude;
    @Column(name = "user_leader") private boolean leader;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_skill_level") private SkillLevel skillLevel;
    @Column(name = "user_photo") private  String profilePic;
    @Column(name = "user_spotify_id") private  String spotifyID;

    // One user has many posts
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;

    // One user has many comments
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    // Many users can have one signature
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signature_fk")
    private Signature signature;

    // Many users can belong to one group
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_fk")
    private Group group;

    // Many users like many genres
    @ManyToMany
    @JoinTable(name = "tb_user_likes_genre",
            joinColumns = @JoinColumn(name = "user_fk"),
            inverseJoinColumns = @JoinColumn(name = "genre_fk"))
    private List<Genre> likedGenres;

    // Many users like many users
    @ManyToMany
    @JoinTable(name = "tb_user_has_role",
            joinColumns = @JoinColumn(name = "user_fk"),
            inverseJoinColumns = @JoinColumn(name = "role_fk"))
    private List<Role> roles;

    // Many users like many posts
    @ManyToMany
    @JoinTable(name = "tb_user_likes_post",
            joinColumns = @JoinColumn(name = "user_fk"),
            inverseJoinColumns = @JoinColumn(name = "post_fk"))
    private List<Post> likedPosts;

    // Many users like many users
    @ManyToMany
    @JoinTable(name = "tb_user_likes_user",
            joinColumns = @JoinColumn(name = "liker_fk"),
            inverseJoinColumns = @JoinColumn(name = "liked_fk"))
    private List<User> likedUsers;

    // Many users are liked by many users
    @ManyToMany(mappedBy = "likedUsers", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> usersWhoLiked;
}
