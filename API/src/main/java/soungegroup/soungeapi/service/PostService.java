package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.request.PostSaveRequest;
import soungegroup.soungeapi.request.PostUpdateRequest;
import soungegroup.soungeapi.response.PostSimpleResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostService {
    ResponseEntity<Long> save(PostSaveRequest body);

    ResponseEntity<List<PostSimpleResponse>> findAll(Optional<Long> userId, 
                                                     Optional<GenreName> genreName,
                                                     Optional<LocalDateTime> startDate,
                                                     Optional<LocalDateTime> endDate,
                                                     Optional<String> textLike);

    ResponseEntity<Void> update(Long id, PostUpdateRequest body);

    ResponseEntity<Void> delete(Long id);
}
