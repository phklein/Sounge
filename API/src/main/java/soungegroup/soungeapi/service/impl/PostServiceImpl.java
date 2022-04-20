package soungegroup.soungeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soungegroup.soungeapi.adapter.GroupAdapter;
import soungegroup.soungeapi.adapter.PostAdapter;
import soungegroup.soungeapi.model.Group;
import soungegroup.soungeapi.model.Post;
import soungegroup.soungeapi.repository.GroupRepository;
import soungegroup.soungeapi.repository.PostRepository;
import soungegroup.soungeapi.request.GroupSaveRequest;
import soungegroup.soungeapi.request.PostSaveRequest;
import soungegroup.soungeapi.request.PostUpdateRequest;
import soungegroup.soungeapi.response.GroupCsvResponse;
import soungegroup.soungeapi.response.GroupSimpleResponse;
import soungegroup.soungeapi.response.PostSimpleResponse;
import soungegroup.soungeapi.service.GroupService;
import soungegroup.soungeapi.service.PostService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final PostAdapter adapter;

    @Override
    public ResponseEntity<PostSimpleResponse> save(PostSaveRequest body) {
        Post post = adapter.toPost(body);

        if (post != null) {
            post.setPostDateTime(LocalDateTime.now());
            post.setUsersWhoLiked(new ArrayList<>());
            post = repository.save(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(adapter.toSimpleResponse(post));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<List<PostSimpleResponse>> findAll() {
        List<Post> foundPosts = repository.findAll();

        return foundPosts.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK).body(adapter.toSimpleResponse(foundPosts));
    }

    @Override
    public ResponseEntity<PostSimpleResponse> update(Long id, PostUpdateRequest body) {
        Optional<Post> postOptional = repository.findById(id);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setText(body.getText());
            post.setMediaUrl(body.getMediaUrl());
            post = repository.save(post);
            return ResponseEntity.status(HttpStatus.OK).body(adapter.toSimpleResponse(post));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
