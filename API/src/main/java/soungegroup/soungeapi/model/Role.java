package soungegroup.soungeapi.model;

import lombok.*;
import soungegroup.soungeapi.model.relations.ArtistHasRole;
import soungegroup.soungeapi.enums.RoleName;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Role")
@Table(name = "tb_role")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id") private Long id;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role_name") private RoleName name;

    // Many roles are associated to many artists
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ArtistHasRole> artistsAssoc;

    public List<Artist> getArtists() {
        List<Artist> artists = new ArrayList<>();
        artistsAssoc.forEach(ahr -> artists.add(ahr.getArtist()));
        return artists;
    }
}
