package soungegroup.soungeapi.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.request.CommentSaveRequest;
import soungegroup.soungeapi.response.CommentSimpleResponse;

public interface CommentService {
    ResponseEntity<Long> save(Long id, CommentSaveRequest body);

    ResponseEntity<Page<CommentSimpleResponse>> findByPostId(Long viewerId, Long postId, Integer page);

    ResponseEntity<Void> delete(Long postId, Long id);
}
