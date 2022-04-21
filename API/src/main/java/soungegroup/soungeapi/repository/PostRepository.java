package soungegroup.soungeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.model.Post;
import soungegroup.soungeapi.model.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findTop50ByOrderByPostDateTimeDesc();
    List<Post> findTop50ByUserOrderByPostDateTimeDesc(User user);
}
