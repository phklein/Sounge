package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.request.PictureChangeRequest;
import soungegroup.soungeapi.response.GroupPageResponse;
import soungegroup.soungeapi.response.GroupSimpleResponse;

public interface GroupService {
    ResponseEntity<Long> save(GroupSaveRequest body);

    ResponseEntity<GroupPageResponse> findById(Long id);
    ResponseEntity export();

    ResponseEntity<Void> changePicture(Long id, PictureChangeRequest body);

    ResponseEntity<Void> delete(Long id);
}
