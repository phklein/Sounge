package soungegroup.soungeapi.model.relations.idclass;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArtistHasRoleId implements Serializable {
    private Long artist;
    private Long role;
}
