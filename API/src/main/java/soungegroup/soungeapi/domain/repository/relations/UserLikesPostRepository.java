package soungegroup.soungeapi.domain.repository.relations;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.relations.UserLikesPost;

public interface UserLikesPostRepository extends JpaRepository<UserLikesPost, Long> {
}
