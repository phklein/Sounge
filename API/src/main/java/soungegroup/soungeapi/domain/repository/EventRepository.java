package soungegroup.soungeapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
