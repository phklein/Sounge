package soungegroup.soungeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.model.Role;
import soungegroup.soungeapi.response.RoleSimpleResponse;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);

    List<Role> findByNameIn(List<RoleName> nameList);

    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.RoleSimpleResponse(r.id, r.name) " +
            "FROM Role r " +
            "JOIN r.users u " +
            "WHERE u.id = :id")
    List<RoleSimpleResponse> findByUserId(Long id);

    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.RoleSimpleResponse(r.id, r.name) " +
            "FROM Role r " +
            "JOIN r.users u " +
            "JOIN u.group g " +
            "WHERE g.id = :id")
    List<RoleSimpleResponse> findByGroupId(Long id);
}
