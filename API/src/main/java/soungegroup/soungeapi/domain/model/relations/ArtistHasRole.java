package soungegroup.soungeapi.domain.model.relations;

import lombok.*;
import soungegroup.soungeapi.domain.model.relations.idclass.ArtistHasRoleId;
import soungegroup.soungeapi.domain.model.Role;
import soungegroup.soungeapi.domain.model.Artist;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "ArtistHasRole")
@Table(name = "tb_artist_has_role")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ArtistHasRoleId.class)
public class ArtistHasRole {
    @Id
    @ManyToOne
    @JoinColumn(name = "artist_fk", referencedColumnName = "user_id")
    private Artist artist;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_fk", referencedColumnName = "role_id")
    private Role role;

    @Column(name = "start_date") private LocalDate startDate;
}
