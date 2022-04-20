package soungegroup.soungeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.Post;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByName(GenreName name);
}
