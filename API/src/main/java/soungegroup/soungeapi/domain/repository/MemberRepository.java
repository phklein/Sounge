package soungegroup.soungeapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
