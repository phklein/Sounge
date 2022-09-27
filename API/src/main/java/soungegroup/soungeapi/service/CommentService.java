package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.request.CommentSaveRequest;
import soungegroup.soungeapi.response.CommentSimpleResponse;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    ResponseEntity<Long> save(Long id, CommentSaveRequest body);

    ResponseEntity<List<CommentSimpleResponse>> findByPostId(Optional<Long> viewerId, Long postId);

    ResponseEntity<Void> delete(Long postId, Long id);
}
