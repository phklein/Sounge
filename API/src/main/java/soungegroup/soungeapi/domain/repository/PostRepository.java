package soungegroup.soungeapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
