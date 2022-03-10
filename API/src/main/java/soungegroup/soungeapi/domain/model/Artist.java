package soungegroup.soungeapi.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import soungegroup.soungeapi.domain.model.relations.ArtistHasRole;
import soungegroup.soungeapi.enums.UserType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Artist")
@Table(name = "tb_artist")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Artist extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") private Long id;
    @Column(name = "artist_gender") private Integer gender;

    // Many artists are associated to many roles
    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ArtistHasRole> rolesAssoc;

    public UserType getUserType() {
        return UserType.CREATOR;
    }

    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();
        rolesAssoc.forEach(ahr -> roles.add(ahr.getRole()));
        return roles;
    }
}
