package soungegroup.soungeapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
