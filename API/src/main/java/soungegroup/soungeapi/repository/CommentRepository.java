package soungegroup.soungeapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soungegroup.soungeapi.model.Comment;
import soungegroup.soungeapi.model.Post;
import soungegroup.soungeapi.response.CommentSimpleResponse;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT new soungegroup.soungeapi.response.CommentSimpleResponse(" +
            "c.id, c.text, c.mediaUrl, c.commentDateTime, c.user) " +
            "FROM Comment c " +
            "WHERE c.post = :post " +
            "ORDER BY c.commentDateTime DESC")
    List<CommentSimpleResponse> findByPostOrdered(Post post, Pageable pageable);
}
