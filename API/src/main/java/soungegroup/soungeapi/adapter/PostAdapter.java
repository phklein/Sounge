package soungegroup.soungeapi.adapter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.Post;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.GenreRepository;
import soungegroup.soungeapi.repository.UserRepository;
import soungegroup.soungeapi.request.PostSaveRequest;
import soungegroup.soungeapi.request.PostUpdateRequest;
import soungegroup.soungeapi.response.GroupSimpleResponse;
import soungegroup.soungeapi.response.PostSimpleResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public PostSimpleResponse toSimpleResponse(Post post) {
        PostSimpleResponse response = mapper.map(post, PostSimpleResponse.class);

        response.setLikeCount(post.getUsersWhoLiked().size());

        return response;
    }

    public List<PostSimpleResponse> toSimpleResponse(List<Post> posts) {
        List<PostSimpleResponse> responseList = new ArrayList<>();

        for (Post p : posts) {
            responseList.add(toSimpleResponse(p));
        }

        return responseList;
    }
}
