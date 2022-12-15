package soungegroup.soungeapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.response.GroupMatchResponse;
import soungegroup.soungeapi.response.GroupPageResponse;
import soungegroup.soungeapi.response.GroupSimpleResponse;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.GroupMatchResponse(" +
            "gp.id, l.id, gp.name, gp.profilePic, l.state, l.city, l.latitude, l.longitude, " +
            "gp.description, SIZE(u), l.signature, gp.creationDate) " +
            "FROM Group gp " +
            "JOIN gp.users l ON l.leader = TRUE " +
            "JOIN gp.users u " +
            "WHERE u.id <> :userId")
    List<GroupMatchResponse> findMatchList(Long userId);

    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.GroupSimpleResponse(" +
            "g.id, g.name, g.profilePic) " +
            "FROM Group g " +
            "WHERE LOWER(g.name) LIKE CONCAT('%', LOWER(:nameLike), '%')")
    Page<GroupSimpleResponse> findByName(String nameLike, Pageable pageable);

    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.GroupPageResponse(" +
            "g.id, g.name, g.description, g.creationDate, g.profilePic, g.banner) " +
            "FROM Group g " +
            "WHERE g.id = :id")
    Optional<GroupPageResponse> findPage(Long id);

    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.GroupSimpleResponse(" +
            "g.id, g.name, g.profilePic) " +
            "FROM Group g " +
            "JOIN g.users u " +
            "WHERE u.id = :id")
    Optional<GroupSimpleResponse> findByUserId(Long id);
}
