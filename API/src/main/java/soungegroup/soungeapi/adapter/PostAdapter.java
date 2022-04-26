package soungegroup.soungeapi.adapter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.Post;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.GenreRepository;
import soungegroup.soungeapi.repository.UserRepository;
import soungegroup.soungeapi.request.PostSaveRequest;
import soungegroup.soungeapi.response.PostSimpleResponse;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostAdapter {
    private final ModelMapper mapper;

    private final GenreRepository genreRepository;
    private final UserRepository userRepository;

    public Post toPost(PostSaveRequest postSaveRequest) {
        Post post = mapper.map(postSaveRequest, Post.class);

        List<Genre> genres = new ArrayList<>();

        for (GenreName gn : postSaveRequest.getGenres()) {
            Optional<Genre> genre = genreRepository.findByName(gn);
            genre.ifPresent(genres::add);
        }

        Optional<User> userOptional = userRepository.findById(postSaveRequest.getUserId());

        if (genres.size() < postSaveRequest.getGenres().size() || userOptional.isEmpty()) {
            return null;
        }

        post.setGenres(genres);
        post.setUser(userOptional.get());

        return post;
    }

    public PostSimpleResponse toSimpleResponse(Post post, Optional<User> viewerOptional) {
        PostSimpleResponse response = mapper.map(post, PostSimpleResponse.class);
        response.setLikeCount(post.getUsersWhoLiked().size());
        response.setCommentCount(post.getComments().size());
        response.setHoursPast(Duration.between(post.getPostDateTime(), LocalDateTime.now()).toHours());
        viewerOptional.ifPresent(u -> response.setHasLiked(post.getUsersWhoLiked().contains(u)));

        return response;
    }

    public List<PostSimpleResponse> toSimpleResponse(List<Post> posts, Optional<User> viewerOptional) {
        List<PostSimpleResponse> responseList = new ArrayList<>();
        posts = posts.stream()
                .sorted(Comparator.comparing(Post::getPostDateTime).reversed())
                .collect(Collectors.toList());
        posts.forEach(p -> responseList.add(toSimpleResponse(p, viewerOptional)));
        return responseList;
    }
}
