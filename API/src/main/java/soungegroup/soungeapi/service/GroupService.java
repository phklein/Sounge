package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.request.GroupPageUpdateRequest;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.request.PictureUpdateRequest;
import soungegroup.soungeapi.response.GroupMatchResponse;
import soungegroup.soungeapi.response.GroupPageResponse;
import soungegroup.soungeapi.response.GroupSimpleResponse;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    ResponseEntity<Long> save(GroupSaveRequest body);

    ResponseEntity<GroupPageResponse> findPageById(Long id);
    ResponseEntity<List<GroupMatchResponse>> findMatchList(Long userId,
                                                           Integer maxDistance,
                                                           Optional<GenreName> genreName,
                                                           Optional<Integer> minSize,
                                                           Optional<Integer> maxSize,
                                                           Optional<RoleName> missingRoleName);
    ResponseEntity<List<GroupSimpleResponse>> findByName(String nameLike);

    ResponseEntity<Void> update(Long id, GroupPageUpdateRequest body);
    ResponseEntity<Void> updatePicture(Long id, PictureUpdateRequest body);

    ResponseEntity<Void> delete(Long id);

    ResponseEntity<Long> upload(Long id, String file);
}
