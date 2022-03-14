package soungegroup.soungeapi.model.relations.idclass;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLikesUserId implements Serializable {
    private Long liker;
    private Long liked;
}
