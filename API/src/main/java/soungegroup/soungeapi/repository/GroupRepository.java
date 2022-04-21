package soungegroup.soungeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.response.GroupCsvResponse;
import soungegroup.soungeapi.response.GroupSimpleResponse;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
<<<<<<< HEAD
    List<Group> findGroupByEmailAndPasswordHash(String login, String passwordHash);
=======
    @Query("select new soungegroup.soungeapi.response.GroupCsvResponse(" +
            "g.id, g.name, g.description, g.creationDate) " +
            "from Group g")
    List<GroupCsvResponse> findAllCsv();
>>>>>>> develop
}
