package soungegroup.soungeapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.response.GroupSimpleResponse;
import soungegroup.soungeapi.service.GroupService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService service;

    @GetMapping
    public ResponseEntity<List<GroupSimpleResponse>> findAll() {
        return service.findAll();
    }

    @GetMapping("/report")
    public ResponseEntity getReport() {
        return service.export();
    }

    @PostMapping
    public ResponseEntity<GroupSimpleResponse> save(@RequestBody @Valid GroupSaveRequest body) {
        return service.save(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
