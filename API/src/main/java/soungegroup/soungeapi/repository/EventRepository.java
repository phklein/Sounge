package soungegroup.soungeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
