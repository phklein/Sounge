package soungegroup.soungeapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.request.GroupPageUpdateRequest;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.request.PictureUpdateRequest;
import soungegroup.soungeapi.response.GroupPageResponse;
import soungegroup.soungeapi.service.GroupService;

import javax.validation.Valid;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
@CrossOrigin
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
    public ResponseEntity<String> getReport() {
        return service.export();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody @Valid GroupPageUpdateRequest body) {
        return service.update(id, body);
    }

    @PatchMapping("/{id}/photo")
    public ResponseEntity<Void> updatePicture(@PathVariable Long id,
                                              @RequestBody @Valid PictureUpdateRequest body) {
        return service.updatePicture(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
