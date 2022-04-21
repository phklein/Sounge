package soungegroup.soungeapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.response.GroupPageResponse;
import soungegroup.soungeapi.response.GroupSimpleResponse;
import soungegroup.soungeapi.service.GroupService;

import javax.validation.Valid;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService service;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody @Valid GroupSaveRequest body) {
        return service.save(body);
    }
    @GetMapping("/{id}")
    public ResponseEntity<GroupPageResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/report")
    public ResponseEntity getReport() {
        return service.export();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
