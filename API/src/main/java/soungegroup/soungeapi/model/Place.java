package soungegroup.soungeapi.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import soungegroup.soungeapi.enums.UserType;

import javax.persistence.*;

@Entity(name = "Place")
@Table(name = "tb_place")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Place extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") private Long id;

    @Override
    public UserType getUserType() {
        return UserType.PLACE;
    }
}
