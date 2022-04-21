package soungegroup.soungeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.model.Post;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.response.GroupCsvResponse;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
}
