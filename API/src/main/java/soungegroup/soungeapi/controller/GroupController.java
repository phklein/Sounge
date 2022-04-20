package soungegroup.soungeapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.response.GroupSimpleResponse;
import soungegroup.soungeapi.service.GroupService;

import javax.validation.Valid;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService service;

    @PostMapping
    public ResponseEntity<GroupSimpleResponse> save(@RequestBody @Valid GroupSaveRequest body) {
        return service.save(body);
    }

    @PatchMapping("/{id}/members/{memberId}")
    public ResponseEntity<Void> addMember(@PathVariable Long id,
                                          @PathVariable Long memberId) {
        return service.addMember(id, memberId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @GetMapping("/report")
    public ResponseEntity getReport() {
        return service.export();
    }
}
