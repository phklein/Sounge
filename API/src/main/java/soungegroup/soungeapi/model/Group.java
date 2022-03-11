package soungegroup.soungeapi.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.UserType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "Group")
@Table(name = "tb_group")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Group extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") private Long id;

    // One group has many members
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Artist> members;

    @Override
    public UserType getUserType() {
        return UserType.GROUP;
    }
}
