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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GenreName getName() {
        return name;
    }

    public void setName(GenreName name) {
        this.name = name;
    }

    public List<User> getUsersWhoLike() {
        return usersWhoLike;
    }

    public void setUsersWhoLike(List<User> usersWhoLike) {
        this.usersWhoLike = usersWhoLike;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
