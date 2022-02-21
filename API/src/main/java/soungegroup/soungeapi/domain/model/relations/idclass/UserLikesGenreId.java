package soungegroup.soungeapi.domain.model.relations.idclass;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLikesGenreId implements Serializable {
    private Long user;
    private Long genre;
}
