package soungegroup.soungeapi.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soungegroup.soungeapi.model.Comment;
import soungegroup.soungeapi.request.CommentSaveRequest;
import soungegroup.soungeapi.util.Mapper;

@Component
@RequiredArgsConstructor
public class CommentAdapter {
    public Comment toComment (CommentSaveRequest commentSaveRequest) {
        return Mapper.INSTANCE.map(commentSaveRequest, Comment.class);
    }
}
