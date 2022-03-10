package soungegroup.soungeapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.Studio;

public interface StudioRepository extends JpaRepository<Studio, Long> {
}
