package soungegroup.soungeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.response.UserCsvResponse;
import soungegroup.soungeapi.response.UserLoginResponse;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select new soungegroup.soungeapi.response.UserLoginResponse(" +
            "u.id, u.name) " +
            "from User u where u.email = ?1 and u.password = ?2")
    List<UserLoginResponse> findUserByEmailAndPassword(String email, String password);

    @Query("select new soungegroup.soungeapi.response.UserCsvResponse(" +
            "u.id, u.name, u.sex, " +
            "u.description, u.birthDate, " +
            "u.state, u.city, u.leader, u.skillLevel) " +
            "from User u")
    List<UserCsvResponse> findAllCsv();
}
