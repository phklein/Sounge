package soungegroup.soungeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
