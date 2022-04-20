package soungegroup.soungeapi.service;

import org.springframework.http.ResponseEntity;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.response.GroupSimpleResponse;

import java.util.List;

public interface GroupService {
    ResponseEntity<GroupSimpleResponse> save(GroupSaveRequest body);

    ResponseEntity<List<GroupSimpleResponse>> findAll();
    ResponseEntity export();

    ResponseEntity<Void> delete(Long id);
}
