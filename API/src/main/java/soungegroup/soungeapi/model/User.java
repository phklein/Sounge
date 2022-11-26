package soungegroup.soungeapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import soungegroup.soungeapi.enums.Sex;
import soungegroup.soungeapi.enums.SkillLevel;
import soungegroup.soungeapi.enums.State;
import soungegroup.soungeapi.util.PilhaObj;

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
    @Column(name = "user_latitude") private Double latitude;
    @Column(name = "user_longitude") private Double longitude;
    @Column(name = "user_leader") private boolean leader;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_skill_level") private SkillLevel skillLevel;
    @Column(name = "user_photo") private String profilePic;
    @Column(name = "user_banner") private String banner;
    @Column(name = "user_spotify_id") private  String spotifyID;
    @Transient private PilhaObj<User> recentLikes = new PilhaObj<User>(1) ;
    @Column(name = "user_phone_number") private String phoneNumber;

    // One user has many posts
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;

    // One user has many comments
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    // Many users can have one signature
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    @ManyToMany
    @JoinTable(name = "tb_user_likes_comment",
            joinColumns = @JoinColumn(name = "user_fk"),
            inverseJoinColumns = @JoinColumn(name = "comment_fk"))
    private List<Comment> likedComments;

    // Many users like many users
    @ManyToMany
    @JoinTable(name = "tb_user_likes_user",
            joinColumns = @JoinColumn(name = "liker_fk"),
            inverseJoinColumns = @JoinColumn(name = "liked_fk"))
    private List<User> likedUsers;

    // Many users are liked by many users
    @ManyToMany(mappedBy = "likedUsers", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> usersWhoLiked;

    // One user may receive many notifications
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Notification> notificationsReceived;

    // One user may send many notifications
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Notification> notificationsSent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public boolean isLeader() {
        return leader;
    }

    public void setLeader(boolean leader) {
        this.leader = leader;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getSpotifyID() {
        return spotifyID;
    }

    public void setSpotifyID(String spotifyID) {
        this.spotifyID = spotifyID;
    }

    public PilhaObj<User> getRecentLikes() {
        return recentLikes;
    }

    public void setRecentLikes(PilhaObj<User> recentLikes) {
        this.recentLikes = recentLikes;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Genre> getLikedGenres() {
        return likedGenres;
    }

    public void setLikedGenres(List<Genre> likedGenres) {
        this.likedGenres = likedGenres;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(List<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public List<User> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(List<User> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public List<User> getUsersWhoLiked() {
        return usersWhoLiked;
    }

    public void setUsersWhoLiked(List<User> usersWhoLiked) {
        this.usersWhoLiked = usersWhoLiked;
    }

    public List<Notification> getNotificationsReceived() {
        return notificationsReceived;
    }

    public void setNotificationsReceived(List<Notification> notificationsReceived) {
        this.notificationsReceived = notificationsReceived;
    }

    public List<Notification> getNotificationsSent() {
        return notificationsSent;
    }

    public void setNotificationsSent(List<Notification> notificationsSent) {
        this.notificationsSent = notificationsSent;
    }
}
