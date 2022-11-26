package soungegroup.soungeapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.model.User;
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
            "JOIN gp.genres g " +
            "JOIN gp.users l ON l.leader = TRUE " +
            "JOIN gp.users u " +
            "JOIN u.roles r " +
            "WHERE u.id <> :userId " +
            "AND l NOT IN :likedUsers " +
            "AND (g.name = :genreName OR :genreName IS NUll)  " +
            "AND (SIZE(u) >= :minSize OR :minSize IS NUll)  " +
            "AND (SIZE(u) <= :maxSize OR :maxSize IS NUll)  " +
            "AND (r.name <> :missingRoleName OR :missingRoleName IS NULL)")
    Page<GroupMatchResponse> findMatchList(Long userId,
                                           List<User> likedUsers,
                                           GenreName genreName,
                                           Integer minSize,
                                           Integer maxSize,
                                           RoleName missingRoleName,
                                           Pageable pageable);

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
