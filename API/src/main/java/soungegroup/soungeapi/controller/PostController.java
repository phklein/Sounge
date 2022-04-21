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
    public ResponseEntity<Long> save(@RequestBody @Valid PostSaveRequest body) {
        return service.save(body);
    }

    @GetMapping
    public ResponseEntity<List<PostSimpleResponse>> findAll(@RequestParam Optional<Long> userId) {
        return service.findAll(userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                                     @RequestBody @Valid PostUpdateRequest body) {
        return service.update(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }


    @PostMapping("/{postId}/comments")
    public ResponseEntity<Long> saveComment(@PathVariable Long postId,
                                                             @RequestBody @Valid CommentSaveRequest body) {
        return commentService.save(postId, body);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentSimpleResponse>> findCommentsByPostId(@PathVariable Long postId) {
        return commentService.findByPostId(postId);
    }

    @DeleteMapping("/{postId}/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long postId,
                                              @PathVariable Long id) {
        return commentService.delete(postId, id);
    }
}
