package soungegroup.soungeapi.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "Group")
@Table(name = "tb_group")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id") private Long id;
    @Column(name = "group_name") private String name;
    @Column(name = "group_description") private String description;
    @Column(name = "group_creation_date") private LocalDate creationDate;

    // Many users like many genres
    @ManyToMany
    @JoinTable(name = "tb_group_has_genre",
            joinColumns = @JoinColumn(name = "group_fk"),
            inverseJoinColumns = @JoinColumn(name = "genre_fk"))
    private List<Genre> genres;

    // One group can have many users
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> users;
}
