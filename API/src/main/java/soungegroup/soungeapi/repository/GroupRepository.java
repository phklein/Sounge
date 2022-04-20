package soungegroup.soungeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.response.GroupSimpleResponse;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
