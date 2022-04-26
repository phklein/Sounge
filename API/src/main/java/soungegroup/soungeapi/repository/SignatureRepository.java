package soungegroup.soungeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.model.Signature;

public interface SignatureRepository extends JpaRepository<Signature, Long> {
}
