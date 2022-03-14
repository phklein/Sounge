package soungegroup.soungeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
