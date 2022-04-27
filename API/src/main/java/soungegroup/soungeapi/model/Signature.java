package soungegroup.soungeapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import soungegroup.soungeapi.enums.SignatureType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Signature")
@Table(name = "tb_signature")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Signature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "signature_id") private Long id;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "signature_type") private SignatureType signatureType;
    @Column(name = "signature_expiry_date_time") private LocalDateTime expiryDateTime;

    // One signature can belong to many users
    @OneToMany(mappedBy = "signature", fetch = FetchType.LAZY)
    private List<User> users;

    public boolean isExpired() {
        return this.expiryDateTime.isBefore(LocalDateTime.now());
    }
}
