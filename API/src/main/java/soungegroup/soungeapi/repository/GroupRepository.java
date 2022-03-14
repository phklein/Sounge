package soungegroup.soungeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
