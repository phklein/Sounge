package soungegroup.soungeapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.request.CommentSaveRequest;
import soungegroup.soungeapi.request.PostSaveRequest;
import soungegroup.soungeapi.request.PostUpdateRequest;
import soungegroup.soungeapi.response.CommentSimpleResponse;
import soungegroup.soungeapi.response.PostSimpleResponse;
import soungegroup.soungeapi.service.CommentService;
import soungegroup.soungeapi.service.PostService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService service;
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<PostSimpleResponse> save(@RequestBody @Valid PostSaveRequest body) {
        return service.save(body);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentSimpleResponse> saveComment(@PathVariable Long postId,
                                                             @RequestBody @Valid CommentSaveRequest body) {
        return commentService.save(postId, body);
    }

    @GetMapping
    public ResponseEntity<List<PostSimpleResponse>> findAll(@RequestParam Optional<Long> userId) {
        return service.findAll(userId);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentSimpleResponse>> findCommentsByPostId(@PathVariable Long postId) {
        return commentService.findByPostId(postId);
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

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        return commentService.delete(id);
    }
}
