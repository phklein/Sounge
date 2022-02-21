package soungegroup.soungeapi.domain.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.users.Place;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findByName(String name);
}
