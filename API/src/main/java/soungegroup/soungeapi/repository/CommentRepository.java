package soungegroup.soungeapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.model.Comment;
import soungegroup.soungeapi.model.Post;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostOrderByCommentDateTimeDesc(Post post, Pageable pageable);
}
