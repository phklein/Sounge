package soungegroup.soungeapi.domain.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.users.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUserByLoginAndPasswordHash(String login, String passwordHash);
}
