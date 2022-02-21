package soungegroup.soungeapi.domain.repository.feed;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.feed.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
