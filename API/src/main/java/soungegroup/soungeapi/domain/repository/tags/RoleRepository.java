package soungegroup.soungeapi.domain.repository.tags;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.tags.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
