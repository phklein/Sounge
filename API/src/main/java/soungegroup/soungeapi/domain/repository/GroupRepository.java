package soungegroup.soungeapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
