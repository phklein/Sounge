package soungegroup.soungeapi.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.request.CommentUpdateRequest;
import soungegroup.soungeapi.request.PostSaveRequest;
import soungegroup.soungeapi.request.PostUpdateRequest;
import soungegroup.soungeapi.response.PostSimpleResponse;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PostService {
    ResponseEntity<Long> save(PostSaveRequest body);

    ResponseEntity<Page<PostSimpleResponse>> findAll(Optional<Long> userId,
                                                     Optional<GenreName> genreName,
                                                     Optional<LocalDateTime> startDate,
                                                     Optional<LocalDateTime> endDate,
                                                     Optional<String> textLike,
                                                     Integer page);

    ResponseEntity<Void> update(Long id, PostUpdateRequest body);
    ResponseEntity<Void> update(Long postId, Long id, CommentUpdateRequest body);

    ResponseEntity<Void> delete(Long id);
}
