package soungegroup.soungeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findGroupByEmailAndPasswordHash(String login, String passwordHash);
}
