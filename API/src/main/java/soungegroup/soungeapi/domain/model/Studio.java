package soungegroup.soungeapi.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import soungegroup.soungeapi.enums.UserType;

import javax.persistence.*;

@Entity(name = "Studio")
@Table(name = "tb_studio")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Studio extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") private Long id;

    public UserType getUserType() {
        return UserType.STUDIO;
    }
}
