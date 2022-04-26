package soungegroup.soungeapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.response.GroupPageResponse;
import soungegroup.soungeapi.response.GroupSimpleResponse;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT new soungegroup.soungeapi.response.GroupSimpleResponse(" +
            "g.id, g.name, g.pictureUrl) " +
            "FROM Group g " +
            "WHERE LOWER(g.name) LIKE CONCAT('%', LOWER(:nameLike), '%')")
    List<GroupSimpleResponse> findByName(String nameLike, Pageable pageable);

    @Query("SELECT new soungegroup.soungeapi.response.GroupPageResponse(" +
            "g.id, g.name, g.description, g.creationDate, g.pictureUrl) " +
            "FROM Group g " +
            "WHERE g.id = :id")
    Optional<GroupPageResponse> findPage(Long id);

    @Query("SELECT new soungegroup.soungeapi.response.GroupSimpleResponse(g.id, g.name, g.pictureUrl) " +
            "FROM Group g " +
            "JOIN g.users u " +
            "WHERE u.id = :id")
    Optional<GroupSimpleResponse> findByProfile(Long id);
}
