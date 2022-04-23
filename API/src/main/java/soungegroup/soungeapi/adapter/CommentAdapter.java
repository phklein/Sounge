package soungegroup.soungeapi.adapter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import soungegroup.soungeapi.model.Comment;
import soungegroup.soungeapi.request.CommentSaveRequest;
import soungegroup.soungeapi.response.CommentSimpleResponse;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentAdapter {
    private final ModelMapper mapper;

    public Comment toComment (CommentSaveRequest commentSaveRequest) {
        return mapper.map(commentSaveRequest, Comment.class);
    }

    public CommentSimpleResponse toSimpleResponse(Comment comment) {
        CommentSimpleResponse response = mapper.map(comment, CommentSimpleResponse.class);
        response.setHoursPast(Duration.between(comment.getCommentDateTime(), LocalDateTime.now()).toHours());
        return response;
    }

    public List<CommentSimpleResponse> toSimpleResponse(List<Comment> comments) {
        List<CommentSimpleResponse> responseList = new ArrayList<>();
        comments.forEach(c -> { responseList.add(toSimpleResponse(c)); });
        return responseList;
    }
}
