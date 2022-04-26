package soungegroup.soungeapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.response.GroupCsvResponse;
import soungegroup.soungeapi.response.GroupSimpleResponse;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT new soungegroup.soungeapi.response.GroupSimpleResponse(" +
            "g.id, g.name, g.pictureUrl) " +
            "FROM Group g " +
            "WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :nameLike, '%'))")
    List<GroupSimpleResponse> findByName(String nameLike, Pageable pageable);

    @Query("SELECT new soungegroup.soungeapi.response.GroupCsvResponse(" +
            "g.id, g.name, g.description, g.creationDate) " +
            "FROM Group g")
    List<GroupCsvResponse> findAllCsv(Pageable pageable);
}
