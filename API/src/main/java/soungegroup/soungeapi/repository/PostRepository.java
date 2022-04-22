package soungegroup.soungeapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.Post;
import soungegroup.soungeapi.model.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByOrderByPostDateTimeDesc(Pageable pageable);
    List<Post> findByUserOrderByPostDateTimeDesc(User user, Pageable pageable);

    @Query( "SELECT p FROM Post p JOIN p.genres g " +
            "WHERE g IN :genres " +
            "OR p.user IN :users ORDER BY p.postDateTime DESC" )
    List<Post> findByUserPreferencesOrderByPostDateTimeDesc(@Param("genres") List<Genre> genres,
                                                            @Param("users") List<User> users,
                                                            Pageable pageable);
}
