package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.adapter.CommentAdapter;
import soungegroup.soungeapi.enums.NotificationType;
import soungegroup.soungeapi.model.Comment;
import soungegroup.soungeapi.model.Notification;
import soungegroup.soungeapi.model.Post;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.CommentRepository;
import soungegroup.soungeapi.repository.NotificationRepository;
import soungegroup.soungeapi.repository.PostRepository;
import soungegroup.soungeapi.repository.UserRepository;
import soungegroup.soungeapi.request.CommentSaveRequest;
import soungegroup.soungeapi.response.CommentSimpleResponse;
import soungegroup.soungeapi.service.CommentService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final NotificationRepository notificationRepository;

    private final CommentAdapter adapter;

    @Override
    public ResponseEntity<Long> save(Long id, CommentSaveRequest body) {
        Optional<Post> postOptional = postRepository.findById(id);
        Optional<User> userOptional = userRepository.findById(body.getUserId());

        if (postOptional.isPresent() && userOptional.isPresent()) {
            User user = userOptional.get();
            Post post = postOptional.get();

            Comment comment = adapter.toComment(body);
            comment.setUser(user);
            comment.setPost(post);
            comment.setCommentDateTime(LocalDateTime.now());
            comment = repository.save(comment);

            Notification notification = new Notification();
            notification.setType(NotificationType.COMMENT);
            notification.setText(String.format("%s comentou em seu post", user.getName()));
            notification.setSender(user);
            notification.setReceiver(post.getUser());
            notification.setCreationDateTime(LocalDateTime.now());
            notificationRepository.save(notification);

            return ResponseEntity.status(HttpStatus.CREATED).body(comment.getId());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<Page<CommentSimpleResponse>> findByPostId(Optional<Long> viewerId, Long postId, Integer page) {
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isPresent()) {

            Page<CommentSimpleResponse> comments = repository.findByPostOrdered(
                    postOptional.get(),
                    Pageable.ofSize(50).withPage(page)
            );


            if (viewerId.isPresent()) {
                Optional<User> viewerOptional = userRepository.findById(viewerId.get());

                if (viewerOptional.isPresent()) {
                    User viewer = viewerOptional.get();

                    comments.forEach(c -> c.setHasLiked(viewer.getLikedComments().stream()
                            .anyMatch(lc -> lc.getId().equals(c.getId()))));
                }
            }

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
