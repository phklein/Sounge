package soungegroup.soungeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUserByEmailAndPasswordHash(String login, String passwordHash);
}
