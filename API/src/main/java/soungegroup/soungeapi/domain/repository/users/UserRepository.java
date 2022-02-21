package soungegroup.soungeapi.domain.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.users.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
