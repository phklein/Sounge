package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.request.PostSaveRequest;
import soungegroup.soungeapi.request.PostUpdateRequest;
import soungegroup.soungeapi.response.PostSimpleResponse;

import java.util.List;
import java.util.Optional;

public interface PostService {
    ResponseEntity<PostSimpleResponse> save(PostSaveRequest body);

    ResponseEntity<List<PostSimpleResponse>> findAll(Optional<Long> userId);

    ResponseEntity<PostSimpleResponse> update(Long id, PostUpdateRequest body);

    ResponseEntity<Void> delete(Long id);
}
