package soungegroup.soungeapi.adapter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.model.Comment;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.model.Post;
import soungegroup.soungeapi.model.User;
import soungegroup.soungeapi.repository.GenreRepository;
import soungegroup.soungeapi.repository.UserRepository;
import soungegroup.soungeapi.request.CommentSaveRequest;
import soungegroup.soungeapi.request.PostSaveRequest;
import soungegroup.soungeapi.response.CommentSimpleResponse;
import soungegroup.soungeapi.response.GroupSimpleResponse;
import soungegroup.soungeapi.response.PostSimpleResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentAdapter {
    private final ModelMapper mapper;

    public Comment toComment (CommentSaveRequest commentSaveRequest) {
        return mapper.map(commentSaveRequest, Comment.class);
    }

    public CommentSimpleResponse toSimpleResponse(Comment comment) {
        return mapper.map(comment, CommentSimpleResponse.class);
    }

    public List<CommentSimpleResponse> toSimpleResponse(List<Comment> comments) {
        return mapper.map(comments, new TypeToken<List<CommentSimpleResponse>>() {}.getType());
    }
}
