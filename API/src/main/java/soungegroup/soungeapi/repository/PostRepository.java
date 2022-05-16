package soungegroup.soungeapi.repository;

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
    @Query("SELECT p " +
            "FROM Post p " +
            "JOIN p.genres g " +
            "WHERE g IN :genres " +
            "OR p.user IN :users " +
            "ORDER BY p.postDateTime DESC")
    List<Post> findAllFilteredByUserOrdered(List<Genre> genres,
                                                          List<User> users,
                                                          Pageable pageable);

    @Query("SELECT p "+
            "FROM Post p " +
            "JOIN p.genres g " +
            "WHERE (g.name = :genreName OR :genreName IS NULL) " +
            "AND (p.postDateTime >= :startDateTime OR :startDateTime IS NULL) " +
            "AND (p.postDateTime <= :endDateTime OR :endDateTime IS NULL) " +
            "AND (LOWER(p.text) LIKE CONCAT('%', LOWER(:textLike), '%') OR :textLike IS NULL) " +
            "ORDER BY p.postDateTime DESC")
    List<Post> findAllFilteredOrdered(GenreName genreName,
                                                    LocalDateTime startDateTime,
                                                    LocalDateTime endDateTime,
                                                    String textLike,
                                                    Pageable pageable);

    @Query("SELECT p " +
            "FROM Post p " +
            "WHERE p.user.id = :userId " +
            "ORDER BY p.postDateTime DESC")
    List<Post> findByUserIdOrdered(Long userId,
                                                 Pageable pageable);
@Query("SELECT p " +
        "FROM Post p " +
        "WHERE p.group.id = :userId " +
        "ORDER BY p.postDateTime DESC")
    List<Post> findByGroupIdOrdered(Long userId,
        Pageable pageable);
        }