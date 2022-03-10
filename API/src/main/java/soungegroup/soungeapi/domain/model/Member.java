package soungegroup.soungeapi.domain.model;

import lombok.*;
import soungegroup.soungeapi.domain.model.relations.MemberHasRole;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Member")
@Table(name = "tb_member")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id") private Long id;
    @Column(name = "member_name") private String name;
    @Column(name = "member_birth_date") private LocalDate birth_date;

    // Many members belong to one group
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_fk")
    private Group group;

    // One member can have one profile
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_fk", referencedColumnName = "user_id")
    private User profile;

    // Many members are associated to many roles
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MemberHasRole> rolesAssoc;

    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();
        rolesAssoc.forEach(mhr -> roles.add(mhr.getRole()));
        return roles;
    }
}
