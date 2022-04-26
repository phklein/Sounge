package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.adapter.CommentAdapter;
import soungegroup.soungeapi.model.Comment;
import soungegroup.soungeapi.model.Post;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.CommentRepository;
import soungegroup.soungeapi.repository.PostRepository;
import soungegroup.soungeapi.repository.UserRepository;
import soungegroup.soungeapi.request.CommentSaveRequest;
import soungegroup.soungeapi.response.CommentSimpleResponse;
import soungegroup.soungeapi.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private static final Pageable PAGEABLE = Pageable.ofSize(10);

    private final CommentRepository repository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentAdapter adapter;

    @Override
    public ResponseEntity<Long> save(Long id, CommentSaveRequest body) {
        Optional<Post> postOptional = postRepository.findById(id);
        Optional<User> userOptional = userRepository.findById(body.getUserId());

        if (postOptional.isPresent() && userOptional.isPresent()) {
            Comment comment = adapter.toComment(body);
            comment.setUser(userOptional.get());
            comment.setPost(postOptional.get());
            comment.setCommentDateTime(LocalDateTime.now());
            comment = repository.save(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(comment.getId());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<List<CommentSimpleResponse>> findByPostId(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isPresent()) {
            List<CommentSimpleResponse> comments = repository.findByPostOrdered(
                    postOptional.get(),
                    PAGEABLE
            );

            return comments.isEmpty() ?
                    ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                    ResponseEntity.status(HttpStatus.OK).body(comments);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<Void> delete(Long postId, Long id) {
        if (repository.existsById(id) && postRepository.existsById(postId)) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
