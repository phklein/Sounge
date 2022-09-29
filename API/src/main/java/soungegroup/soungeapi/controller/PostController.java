package soungegroup.soungeapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.request.CommentSaveRequest;
import soungegroup.soungeapi.request.PostSaveRequest;
import soungegroup.soungeapi.request.PostUpdateRequest;
import soungegroup.soungeapi.response.CommentSimpleResponse;
import soungegroup.soungeapi.response.PostSimpleResponse;
import soungegroup.soungeapi.service.CommentService;
import soungegroup.soungeapi.service.PostService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@CrossOrigin
public class PostController {
    private final PostService service;
    private final CommentService commentService;

    @Autowired
    public PostController(PostService service, CommentService commentService) {
        this.service = service;
        this.commentService = commentService;
    }

    // Posts
    @PostMapping
    public ResponseEntity<Long> save(@RequestBody @Valid PostSaveRequest body) {
        return service.save(body);
    }

    @GetMapping
    public ResponseEntity<Page<PostSimpleResponse>> findAll(@RequestParam Optional<Long> userId,
                                                            @RequestParam Optional<GenreName> genre,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                            Optional<LocalDateTime> startDateTime,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                            Optional<LocalDateTime> endDateTime,
                                                            @RequestParam Optional<String> textLike,
                                                            @RequestParam Integer page) {
        return service.findAll(userId, genre, startDateTime, endDateTime, textLike, page);
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

    // Comments
    @PostMapping("/{postId}/comments")
    public ResponseEntity<Long> saveComment(@PathVariable Long postId,
                                            @RequestBody @Valid CommentSaveRequest body) {
        return commentService.save(postId, body);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<Page<CommentSimpleResponse>> findCommentsByPostId(@RequestParam Optional<Long> viewerId,
                                                                            @PathVariable Long postId,
                                                                            @RequestParam Integer page) {
        return commentService.findByPostId(viewerId, postId, page);
    }

    @DeleteMapping("/{postId}/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long postId,
                                              @PathVariable Long id) {
        return commentService.delete(postId, id);
    }
}
