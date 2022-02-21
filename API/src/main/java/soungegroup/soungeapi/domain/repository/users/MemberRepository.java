package soungegroup.soungeapi.domain.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.users.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
