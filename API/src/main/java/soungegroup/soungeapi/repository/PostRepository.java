package soungegroup.soungeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
