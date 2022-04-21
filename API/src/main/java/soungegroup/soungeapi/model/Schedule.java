package soungegroup.soungeapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import soungegroup.soungeapi.enums.Weekday;

import javax.persistence.*;
import java.time.LocalTime;

@Entity(name = "Schedule")
@Table(name = "tb_schedule")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id") private Long id;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "schedule_weekday") private Weekday weekday;
    @Column(name = "schedule_start_time") private LocalTime startTime;
    @Column(name = "schedule_end_time") private LocalTime endTime;

    // Many schedules belong to one user
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_fk")
    private User user;
}
