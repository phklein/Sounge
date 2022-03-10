package soungegroup.soungeapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
