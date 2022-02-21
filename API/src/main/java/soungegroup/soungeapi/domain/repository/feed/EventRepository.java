package soungegroup.soungeapi.domain.repository.feed;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.feed.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
