package soungegroup.soungeapi.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.request.GroupPageUpdateRequest;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.request.PictureUpdateRequest;
import soungegroup.soungeapi.response.GroupMatchResponse;
import soungegroup.soungeapi.response.GroupPageResponse;
import soungegroup.soungeapi.response.GroupSimpleResponse;
import soungegroup.soungeapi.response.PostSimpleResponse;

import java.util.Optional;

public interface GroupService {
    ResponseEntity<Long> save(GroupSaveRequest body);

    ResponseEntity<GroupPageResponse> findPageById(Long viewerId, Long id);
    ResponseEntity<Page<PostSimpleResponse>> getPostsById(Long viewerId, Long id, Integer page);
    ResponseEntity<Page<GroupMatchResponse>> findMatchList(Long userId,
                                                           Integer maxDistance,
                                                           Optional<GenreName> genreName,
                                                           Optional<Integer> minSize,
                                                           Optional<Integer> maxSize,
                                                           Optional<RoleName> missingRoleName,
                                                           Integer page);
    ResponseEntity<Page<GroupSimpleResponse>> findByName(String nameLike, Integer page);

    ResponseEntity<Void> update(Long id, GroupPageUpdateRequest body);
    ResponseEntity<Void> updatePicture(Long id, PictureUpdateRequest body);

    ResponseEntity<Void> delete(Long id);
}
