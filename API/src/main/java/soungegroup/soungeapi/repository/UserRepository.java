package soungegroup.soungeapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.enums.Sex;
import soungegroup.soungeapi.enums.SkillLevel;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.response.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT new soungegroup.soungeapi.response.UserLoginResponse(" +
            "u.id, u.name, u.profilePic) " +
            "FROM User u WHERE u.email = :email AND u.password = :password")
    List<UserLoginResponse> findUserByEmailAndPassword(String email, String password);

    @Query("SELECT new soungegroup.soungeapi.response.UserSimpleResponse(" +
            "u.id, u.name, u.profilePic, u.leader) " +
            "FROM User u " +
            "WHERE LOWER(u.name) LIKE CONCAT('%', LOWER(:nameLike), '%')")
    List<UserSimpleResponse> findByName(String nameLike, Pageable pageable);

    @Query("SELECT new soungegroup.soungeapi.response.UserSimpleResponse(" +
            "u.id, u.name, u.profilePic, u.leader) " +
            "FROM User u " +
            "WHERE u.group.id = :id")
    List<UserSimpleResponse> findByPage(Long id);

    @Query("SELECT new soungegroup.soungeapi.response.UserProfileResponse(" +
            "u.id, u.name, u.profilePic, u.leader, u.spotifyID, u.description, u.skillLevel, u.birthDate) " +
            "FROM User u " +
            "WHERE u.id = :id")
    Optional<UserProfileResponse> findProfile(Long id);

    @Query("SELECT new soungegroup.soungeapi.response.UserMatchResponse(" +
            "u.id, u.name, u.profilePic, u.leader, u.spotifyID, u.description, u.skillLevel, u.birthDate AS bd) " +
            "FROM User u " +
            "JOIN Role r " +
            "WHERE u.id <> :userId " +
            "AND bd >= :minBirthDate " +
            "AND bd <= :maxBirthDate " +
            "AND (r.name = :roleName OR :roleName IS NULL) " +
            "AND (u.sex = :sex OR :sex IS NULL) " +
            "AND (u.skillLevel = :skillLevel OR :skillLevel IS NULL)")
    List<UserMatchResponse> findMatchList(Long userId,
                                          LocalDate minBirthDate,
                                          LocalDate maxBirthDate,
                                          RoleName roleName,
                                          Sex sex,
                                          SkillLevel skillLevel,
                                          Pageable pageable);

    @Query("SELECT new soungegroup.soungeapi.response.UserCsvResponse(" +
            "u.id, u.name, u.sex, " +
            "u.description, u.birthDate, " +
            "u.state, u.city, u.latitude, " +
            "u.longitude, u.leader, u.skillLevel) " +
            "FROM User u")
    List<UserCsvResponse> findAllCsv(Pageable pageable);
}
