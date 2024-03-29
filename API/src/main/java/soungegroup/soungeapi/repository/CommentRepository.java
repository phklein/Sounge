package soungegroup.soungeapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soungegroup.soungeapi.model.Comment;
import soungegroup.soungeapi.model.Post;
import soungegroup.soungeapi.response.CommentSimpleResponse;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.CommentSimpleResponse(" +
            "c.id, c.text, c.mediaUrl, c.commentDateTime, c.user, SIZE(uwl)) " +
            "FROM Comment c " +
            "LEFT JOIN c.usersWhoLiked uwl " +
            "WHERE c.post = :post " +
            "ORDER BY c.commentDateTime DESC")
    Page<CommentSimpleResponse> findByPostOrdered(Post post, Pageable pageable);
}
