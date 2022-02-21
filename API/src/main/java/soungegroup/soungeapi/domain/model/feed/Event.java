package soungegroup.soungeapi.domain.model.feed;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "Event")
@Table(name = "tb_event")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Event extends Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id") private Long id;
    @Column(name = "event_start_date_time") private LocalDateTime startDateTime;
    @Column(name = "event_end_date_time") private LocalDateTime endDateTime;
    @Column(name = "event_people_limit") private Integer peopleLimit;
    @Column(name = "event_entry_price") private BigDecimal entryPrice;
}
