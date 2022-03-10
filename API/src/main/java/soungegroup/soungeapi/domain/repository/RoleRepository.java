package soungegroup.soungeapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
