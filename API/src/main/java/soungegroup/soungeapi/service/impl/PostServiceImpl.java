package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.adapter.PostAdapter;
import soungegroup.soungeapi.model.Post;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.PostRepository;
import soungegroup.soungeapi.repository.UserRepository;
import soungegroup.soungeapi.request.PostSaveRequest;
import soungegroup.soungeapi.request.PostUpdateRequest;
import soungegroup.soungeapi.response.PostSimpleResponse;
import soungegroup.soungeapi.service.PostService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private static final int PAGE_SIZE = 50;

    private final PostRepository repository;
    private final UserRepository userRepository;
    private final PostAdapter adapter;

    @Override
    public ResponseEntity<Long> save(PostSaveRequest body) {
        Post post = adapter.toPost(body);

        if (post != null) {
            post.setPostDateTime(LocalDateTime.now());
            post = repository.save(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(post.getId());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<List<PostSimpleResponse>> findAll(Optional<Long> userId) {
        List<Post> foundPosts;

        if (userId.isPresent()) {
            Optional<User> userOptional = userRepository.findById(userId.get());

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                foundPosts = repository.findByUserPreferencesOrderByPostDateTimeDesc(
                        user.getLikedGenres(),
                        user.getLikedUsers(),
                        Pageable.ofSize(PAGE_SIZE)
                );
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            foundPosts = repository.findByOrderByPostDateTimeDesc(Pageable.ofSize(PAGE_SIZE));
        }

        return foundPosts.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK).body(adapter.toSimpleResponse(foundPosts));
    }

    @Override
    public ResponseEntity<Void> update(Long id, PostUpdateRequest body) {
        Optional<Post> postOptional = repository.findById(id);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setText(body.getText());
            post.setMediaUrl(body.getMediaUrl());
            repository.save(post);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
