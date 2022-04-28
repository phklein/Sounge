package soungegroup.soungeapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.enums.Sex;
import soungegroup.soungeapi.enums.SkillLevel;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.response.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.UserLoginResponse(" +
            "u.id, u.name, u.profilePic, u.leader, SIZE(n), SIZE(m)) " +
            "FROM User u " +
            "LEFT JOIN u.notificationsReceived n ON n.viewed = FALSE AND n.type <> 0 " +
            "LEFT JOIN u.notificationsReceived m ON n.viewed = FALSE AND n.type = 0 " +
            "WHERE u.email = :email AND u.password = :password")
    List<UserLoginResponse> findUserByEmailAndPassword(String email, String password);

    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.UserMatchResponse(" +
            "u.id, u.name, u.profilePic, u.leader, u.sex, u.state, u.city, u.latitude, u.longitude, " +
            "u.spotifyID, u.description, u.signature, u.skillLevel, u.birthDate) " +
            "FROM User u " +
            "JOIN u.roles r " +
            "JOIN u.likedGenres g " +
            "WHERE u.id <> :userId " +
            "AND (u NOT IN :likedUsers OR :likedUsers IS NULL) " +
            "AND (u.birthDate >= :minBirthDate OR :minBirthDate IS NUll)  " +
            "AND (u.birthDate <= :maxBirthDate OR :maxBirthDate IS NUll)  " +
            "AND (r.name = :roleName OR :roleName IS NULL) " +
            "AND (g.name = :genreName OR :genreName IS NULL) " +
            "AND (u.sex = :sex OR :sex IS NULL) " +
            "AND (u.skillLevel = :skillLevel OR :skillLevel IS NULL)")
    List<UserMatchResponse> findMatchList(Long userId,
                                          List<User> likedUsers,
                                          LocalDate minBirthDate,
                                          LocalDate maxBirthDate,
                                          GenreName genreName,
                                          RoleName roleName,
                                          Sex sex,
                                          SkillLevel skillLevel,
                                          Pageable pageable);

    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.UserSimpleResponse(" +
            "u.id, u.name, u.profilePic, u.leader) " +
            "FROM User u " +
            "WHERE :user IN u.likedUsers " +
            "AND :user IN u.usersWhoLiked")
    List<UserSimpleResponse> findContactList(User user, Pageable pageable);

    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.UserSimpleResponse(" +
            "u.id, u.name, u.profilePic, u.leader) " +
            "FROM User u " +
            "WHERE LOWER(u.name) LIKE CONCAT('%', LOWER(:nameLike), '%')")
    List<UserSimpleResponse> findByName(String nameLike, Pageable pageable);

    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.UserSimpleResponse(" +
            "u.id, u.name, u.profilePic, u.leader) " +
            "FROM User u " +
            "JOIN u.group g " +
            "WHERE g.id = :id")
    List<UserSimpleResponse> findByGroupId(Long id);

    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.UserProfileResponse(" +
            "u.id, u.name, u.profilePic, u.banner, u.leader, u.spotifyID, " +
            "u.description, u.skillLevel, u.birthDate) " +
            "FROM User u " +
            "WHERE u.id = :id")
    Optional<UserProfileResponse> findProfile(Long id);

    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.UserCsvResponse(" +
            "u.id, u.name, u.sex, " +
            "u.description, u.birthDate, " +
            "u.state, u.city, u.latitude, " +
            "u.longitude, u.leader, u.skillLevel) " +
            "FROM User u")
    List<UserCsvResponse> findAllCsv(Pageable pageable);
}
