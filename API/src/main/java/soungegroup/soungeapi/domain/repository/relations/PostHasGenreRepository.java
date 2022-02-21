package soungegroup.soungeapi.domain.repository.relations;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.relations.PostHasGenre;

public interface PostHasGenreRepository extends JpaRepository<PostHasGenre, Long> {
}
