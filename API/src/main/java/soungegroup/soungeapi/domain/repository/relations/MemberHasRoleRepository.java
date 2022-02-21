package soungegroup.soungeapi.domain.repository.relations;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.relations.MemberHasRole;

public interface MemberHasRoleRepository extends JpaRepository<MemberHasRole, Long> {
}
