package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.response.GroupSimpleResponse;

public interface GroupService {
    ResponseEntity<GroupSimpleResponse> save(GroupSaveRequest body);
    ResponseEntity<Void> addMember(Long id, Long memberId);
    ResponseEntity<Void> delete(Long id);
    ResponseEntity export();
}
