package soungegroup.soungeapi.domain.repository.tags;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.tags.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
