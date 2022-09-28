package soungegroup.soungeapi.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.Post;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.GenreRepository;
import soungegroup.soungeapi.repository.UserRepository;
import soungegroup.soungeapi.request.PostSaveRequest;
import soungegroup.soungeapi.response.GroupSimpleResponse;
import soungegroup.soungeapi.response.PostSimpleResponse;
import soungegroup.soungeapi.response.UserSimpleResponse;
import soungegroup.soungeapi.util.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostAdapter {
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;

    public Post toPost(PostSaveRequest postSaveRequest) {
        Post post = Post.builder()
                .text(postSaveRequest.getText())
                .mediaUrl(postSaveRequest.getMediaUrl())
                .build();

        Optional<User> userOptional = userRepository.findById(postSaveRequest.getUserId());

        if (userOptional.isEmpty()) {
            return null;
        }

        List<Genre> genres = new ArrayList<>(userOptional.get().getLikedGenres());
        post.setGenres(genres);
        post.setUser(userOptional.get());

        return post;
    }

    public List<PostSimpleResponse> toSimpleResponseList(List<Post> foundPosts) {
       List<PostSimpleResponse> aux = new ArrayList<>();
       foundPosts.forEach(post -> {
           PostSimpleResponse postSimpleResponse = Mapper.INSTANCE.map(post, PostSimpleResponse.class);
           if(post.getUser() != null) {
               postSimpleResponse.setUser(Mapper.INSTANCE.map(post.getUser(), UserSimpleResponse.class));
           }else{
               postSimpleResponse.setGroup(Mapper.INSTANCE.map(post.getGroup(), GroupSimpleResponse.class));
           }
           aux.add(postSimpleResponse);
       });
       return aux;
    }

}
