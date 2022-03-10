package soungegroup.soungeapi.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import soungegroup.soungeapi.enums.UserType;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Group")
@Table(name = "tb_group")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Group extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") private Long id;

    // One group has many members
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Member> members;

    public UserType getUserType() {
        return UserType.CREATOR;
    }
}
