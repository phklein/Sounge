package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.request.GroupPageUpdateRequest;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.request.PictureUpdateRequest;
import soungegroup.soungeapi.response.GroupPageResponse;

public interface GroupService {
    ResponseEntity<Long> save(GroupSaveRequest body);

    ResponseEntity<GroupPageResponse> findById(Long id);
    ResponseEntity<String> export();

    ResponseEntity<Void> update(Long id, GroupPageUpdateRequest body);
    ResponseEntity<Void> updatePicture(Long id, PictureUpdateRequest body);

    ResponseEntity<Void> delete(Long id);
}
