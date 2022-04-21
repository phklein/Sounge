package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.request.CommentSaveRequest;
import soungegroup.soungeapi.request.PostSaveRequest;
import soungegroup.soungeapi.request.PostUpdateRequest;
import soungegroup.soungeapi.response.CommentSimpleResponse;
import soungegroup.soungeapi.response.PostSimpleResponse;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    ResponseEntity<CommentSimpleResponse> save(Long id, CommentSaveRequest body);

    ResponseEntity<List<CommentSimpleResponse>> findByPostId(Long postId);

    ResponseEntity<Void> delete(Long id);
}
