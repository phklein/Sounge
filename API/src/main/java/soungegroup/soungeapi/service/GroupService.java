package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.request.UpdateGroupPageRequest;
import soungegroup.soungeapi.response.GroupPageResponse;

public interface GroupService {
    ResponseEntity<Long> save(GroupSaveRequest body);

    ResponseEntity<GroupPageResponse> findById(Long id);
    ResponseEntity<String> export();

    ResponseEntity<Void> update(Long id, UpdateGroupPageRequest body);

    ResponseEntity<Void> delete(Long id);
}
