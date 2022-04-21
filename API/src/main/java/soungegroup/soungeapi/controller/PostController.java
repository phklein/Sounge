package soungegroup.soungeapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.request.PostSaveRequest;
import soungegroup.soungeapi.request.PostUpdateRequest;
import soungegroup.soungeapi.response.PostSimpleResponse;
import soungegroup.soungeapi.service.PostService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService service;

    @PostMapping
    public ResponseEntity<PostSimpleResponse> save(@RequestBody @Valid PostSaveRequest body) {
        return service.save(body);
    }

    @GetMapping
    public ResponseEntity<List<PostSimpleResponse>> findAll(@RequestParam Optional<Long> userId) {
        return service.findAll(userId);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostSimpleResponse>> findByUserId(@PathVariable Long userId) {
        return service.findByUserId(userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostSimpleResponse> update(@PathVariable Long id,
                                                     @RequestBody @Valid PostUpdateRequest body) {
        return service.update(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
