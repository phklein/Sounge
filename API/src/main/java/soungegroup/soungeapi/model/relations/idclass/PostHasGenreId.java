package soungegroup.soungeapi.model.relations.idclass;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostHasGenreId implements Serializable {
    private Long post;
    private Long genre;
}
