package soungegroup.soungeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
