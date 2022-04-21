package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.request.CommentSaveRequest;
import soungegroup.soungeapi.response.CommentSimpleResponse;

import java.util.List;

public interface CommentService {
    ResponseEntity<CommentSimpleResponse> save(Long id, CommentSaveRequest body);

    ResponseEntity<List<CommentSimpleResponse>> findByPostId(Long postId);

    ResponseEntity<Void> delete(Long postId, Long id);
}
