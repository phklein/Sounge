package soungegroup.soungeapi.domain.model.tags;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import soungegroup.soungeapi.domain.model.users.User;
import soungegroup.soungeapi.enums.Weekday;

import javax.persistence.*;
import java.time.LocalTime;

@Entity(name = "Schedule")
@Table(name = "tb_schedule")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id") private Long id;
    @Column(name = "schedule_weekday") private Weekday weekday;
    @Column(name = "schedule_start_time") private LocalTime startTime;
    @Column(name = "schedule_end_time") private LocalTime endTime;

    // Many schedules belong to one user
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_fk")
    private User user;
}
