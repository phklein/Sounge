package soungegroup.soungeapi.model;

import lombok.*;
import soungegroup.soungeapi.enums.GenreName;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "Group")
@Table(name = "tb_group")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id") private Long id;
    @Column(name = "group_name") private String name;
    @Column(name = "group_description") private String description;
    @Column(name = "group_birth_date") private LocalDate birthDate;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "group_genre") private GenreName genre;

    // One group has many members
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Artist> members;
}
