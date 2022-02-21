package soungegroup.soungeapi.domain.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.users.Studio;

public interface StudioRepository extends JpaRepository<Studio, Long> {
}
