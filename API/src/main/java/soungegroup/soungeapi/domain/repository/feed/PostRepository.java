package soungegroup.soungeapi.domain.repository.feed;

import org.springframework.data.jpa.repository.JpaRepository;
import soungegroup.soungeapi.domain.model.feed.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
