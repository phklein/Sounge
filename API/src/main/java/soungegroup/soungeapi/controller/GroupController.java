package soungegroup.soungeapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.enums.RoleName;
import soungegroup.soungeapi.request.GroupPageUpdateRequest;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.request.PictureUpdateRequest;
import soungegroup.soungeapi.response.GroupMatchResponse;
import soungegroup.soungeapi.response.GroupPageResponse;
import soungegroup.soungeapi.response.GroupSimpleResponse;
import soungegroup.soungeapi.response.PostSimpleResponse;
import soungegroup.soungeapi.service.GroupService;

import javax.validation.Valid;
import java.util.Optional;

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
    public ResponseEntity<GroupPageResponse> findById(@RequestParam Long viewerId,
                                                      @PathVariable Long id) {
        return service.findPageById(viewerId, id);
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<Page<PostSimpleResponse>> getPostsById(@RequestParam Long viewerId,
                                                                 @PathVariable Long id,
                                                                 @RequestParam Integer page) {
        return service.getPostsById(viewerId, id, page);
    }

    @GetMapping("/match")
    public ResponseEntity<Page<GroupMatchResponse>> findMatchList(@RequestParam Long userId,
                                                                  @RequestParam Integer maxDistance,
                                                                  @RequestParam Optional<GenreName> genreName,
                                                                  @RequestParam Optional<Integer> minSize,
                                                                  @RequestParam Optional<Integer> maxSize,
                                                                  @RequestParam Optional<RoleName> missingRoleName,
                                                                  @RequestParam Integer page) {
        return service.findMatchList(userId, maxDistance, genreName, minSize, maxSize, missingRoleName, page);
    }

    @GetMapping
    public ResponseEntity<Page<GroupSimpleResponse>> findByName(@RequestParam String nameLike,
                                                                @RequestParam Integer page) {
        return service.findByName(nameLike, page);
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
