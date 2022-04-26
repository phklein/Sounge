package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.request.GroupPageUpdateRequest;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.request.PictureUpdateRequest;
import soungegroup.soungeapi.response.GroupPageResponse;
import soungegroup.soungeapi.response.GroupSimpleResponse;

import java.util.List;

public interface GroupService {
    ResponseEntity<Long> save(GroupSaveRequest body);

    ResponseEntity<GroupPageResponse> findPageById(Long id);
    ResponseEntity<List<GroupSimpleResponse>> findByName(String nameLike);

    ResponseEntity<Void> update(Long id, GroupPageUpdateRequest body);
    ResponseEntity<Void> updatePicture(Long id, PictureUpdateRequest body);

    ResponseEntity<Void> delete(Long id);
}
