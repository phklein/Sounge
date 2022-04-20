package soungegroup.soungeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
}
