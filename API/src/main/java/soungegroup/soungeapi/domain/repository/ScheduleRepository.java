package soungegroup.soungeapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
