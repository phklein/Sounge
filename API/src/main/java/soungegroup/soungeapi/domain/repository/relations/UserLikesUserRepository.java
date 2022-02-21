package soungegroup.soungeapi.domain.repository.relations;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.relations.UserLikesUser;

public interface UserLikesUserRepository extends JpaRepository<UserLikesUser, Long> {
}
