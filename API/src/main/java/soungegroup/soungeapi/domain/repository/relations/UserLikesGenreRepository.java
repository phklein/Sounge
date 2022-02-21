package soungegroup.soungeapi.domain.repository.relations;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.relations.UserLikesGenre;

public interface UserLikesGenreRepository extends JpaRepository<UserLikesGenre, Long> {
}
