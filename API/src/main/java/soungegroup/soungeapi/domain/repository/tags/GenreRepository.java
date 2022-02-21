package soungegroup.soungeapi.domain.repository.tags;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.tags.Genre;
import soungegroup.soungeapi.enums.GenreName;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(GenreName name);
}
