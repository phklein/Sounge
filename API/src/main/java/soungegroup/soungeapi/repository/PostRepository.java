package soungegroup.soungeapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.Post;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.response.PostSimpleResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.PostSimpleResponse(" +
            "p.id, p.text, p.mediaUrl, p.postDateTime, p.user, SIZE(uwl), SIZE(c)) " +
            "FROM Post p " +
            "LEFT JOIN p.usersWhoLiked uwl " +
            "LEFT JOIN p.comments c " +
            "ORDER BY p.postDateTime DESC")
    Page<PostSimpleResponse> findAllOrdered(Pageable pageable);

    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.PostSimpleResponse(" +
            "p.id, p.text, p.mediaUrl, p.postDateTime, p.user, SIZE(uwl), SIZE(c)) " +
            "FROM Post p " +
            "LEFT JOIN p.usersWhoLiked uwl " +
            "LEFT JOIN p.comments c " +
            "JOIN p.genres g " +
            "WHERE g IN :genres " +
            "OR p.user IN :users " +
            "ORDER BY p.postDateTime DESC")
    Page<PostSimpleResponse> findAllFilteredByUserOrdered(List<Genre> genres,
                                                          List<User> users,
                                                          Pageable pageable);

    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.PostSimpleResponse(" +
            "p.id, p.text, p.mediaUrl, p.postDateTime, p.user, SIZE(uwl), SIZE(c)) " +
            "FROM Post p " +
            "LEFT JOIN p.usersWhoLiked uwl " +
            "LEFT JOIN p.comments c " +
            "JOIN p.genres g " +
            "WHERE (g.name = :genreName OR :genreName IS NULL) " +
            "AND (p.postDateTime >= :startDateTime OR :startDateTime IS NULL) " +
            "AND (p.postDateTime <= :endDateTime OR :endDateTime IS NULL) " +
            "AND (LOWER(p.text) LIKE CONCAT('%', LOWER(:textLike), '%') OR :textLike IS NULL) " +
            "ORDER BY p.postDateTime DESC")
    Page<PostSimpleResponse> findAllFilteredOrdered(GenreName genreName,
                                                    LocalDateTime startDateTime,
                                                    LocalDateTime endDateTime,
                                                    String textLike,
                                                    Pageable pageable);

    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.PostSimpleResponse(" +
            "p.id, p.text, p.mediaUrl, p.postDateTime, p.user, SIZE(uwl), SIZE(c)) " +
            "FROM Post p " +
            "LEFT JOIN p.usersWhoLiked uwl " +
            "LEFT JOIN p.comments c " +
            "WHERE p.user.id = :userId " +
            "ORDER BY p.postDateTime DESC")
    Page<PostSimpleResponse> findByUserIdOrdered(Long userId,
                                                 Pageable pageable);

    @Query("SELECT DISTINCT new soungegroup.soungeapi.response.PostSimpleResponse( " +
            "p.id, p.text, p.mediaUrl, p.postDateTime, p.group, SIZE(uwl), SIZE(c)) " +
            "FROM Post p " +
            "LEFT JOIN p.usersWhoLiked uwl " +
            "LEFT JOIN p.comments c " +
            "WHERE p.group.id = :groupId " +
            "ORDER BY p.postDateTime DESC")
    Page<PostSimpleResponse> findByGroupIdOrdered(Long groupId,
                                                  Pageable pageable);
}