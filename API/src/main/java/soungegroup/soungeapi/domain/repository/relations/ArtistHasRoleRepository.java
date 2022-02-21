package soungegroup.soungeapi.domain.repository.relations;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.relations.ArtistHasRole;

public interface ArtistHasRoleRepository extends JpaRepository<ArtistHasRole, Long> {
}
