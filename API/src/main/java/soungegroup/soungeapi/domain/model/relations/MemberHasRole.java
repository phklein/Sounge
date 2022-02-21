package soungegroup.soungeapi.domain.model.relations;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import soungegroup.soungeapi.domain.model.relations.idclass.MemberHasRoleId;
import soungegroup.soungeapi.domain.model.tags.Role;
import soungegroup.soungeapi.domain.model.users.Member;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "MemberHasRole")
@Table(name = "tb_member_has_role")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(MemberHasRoleId.class)
public class MemberHasRole {
    @Id
    @ManyToOne
    @JoinColumn(name = "member_fk", referencedColumnName = "member_id")
    private Member member;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_fk", referencedColumnName = "role_id")
    private Role role;

    @Column(name = "start_date") private LocalDate startDate;
}
