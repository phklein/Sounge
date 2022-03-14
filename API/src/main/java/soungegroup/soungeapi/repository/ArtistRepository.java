package soungegroup.soungeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
