package soungegroup.soungeapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import soungegroup.soungeapi.enums.RoleName;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Role")
@Table(name = "tb_role")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id") private Long id;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role_name") private RoleName name;

    // Many roles are associated to many artists
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;
}
