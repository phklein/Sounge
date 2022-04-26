package soungegroup.soungeapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.response.UserCsvResponse;
import soungegroup.soungeapi.response.UserLoginResponse;
import soungegroup.soungeapi.response.UserSimpleResponse;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT new soungegroup.soungeapi.response.UserLoginResponse(" +
            "u.id, u.name, u.profilePic) " +
            "FROM User u WHERE u.email = ?1 AND u.password = ?2")
    List<UserLoginResponse> findUserByEmailAndPassword(String email, String password);

    @Query("SELECT new soungegroup.soungeapi.response.UserSimpleResponse(" +
            "u.id, u.name, u.profilePic, u.leader) " +
            "FROM User u " +
            "WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :nameLike, '%'))")
    List<UserSimpleResponse> findByName(String nameLike, Pageable pageable);

    @Query("SELECT new soungegroup.soungeapi.response.UserCsvResponse(" +
            "u.id, u.name, u.sex, " +
            "u.description, u.birthDate, " +
            "u.state, u.city, u.latitude, " +
            "u.longitude, u.leader, u.skillLevel) " +
            "FROM User u")
    List<UserCsvResponse> findAllCsv();
}
